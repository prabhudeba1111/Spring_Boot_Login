package com.example.loginapi.controller;

import com.example.loginapi.entity.user.Token;
import com.example.loginapi.entity.user.User;
import com.example.loginapi.payload.request.LoginRequest;
import com.example.loginapi.payload.response.JwtResponse;
import com.example.loginapi.payload.request.SignupRequest;
import com.example.loginapi.payload.response.MessageResponse;
import com.example.loginapi.repository.TokenRepository;
import com.example.loginapi.repository.UserRepository;
import com.example.loginapi.security.JwtHelper;
import com.example.loginapi.service.Tokenop;
import com.example.loginapi.service.UserDetailsServiceImpl;
import com.example.loginapi.service.UserVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController{
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    UserVerify userVerify;
    @Autowired
    Tokenop tokenop;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private JwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        if (!userVerify.authenticate(request.getEmail(), request.getPassword())){
            throw new BadCredentialsException("Invalid Username or Password  !!");
        }
        Token token1 = tokenop.findToken(request.getEmail());
        if (token1.getToken()==null) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(request.getEmail());
            String token = helper.generateToken(userDetails);
        }
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token1.getToken())
                .username(token1.getEmail()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/to")
    public void to(@RequestBody Token token){
        Token temp = tokenop.findToken(token.getEmail());
        temp.setToken(token.getToken());
        tokenRepository.save(temp);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Email already in use."));
        }
        if (!request.getPassword().equals(request.getConfirmPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("Passwords don't match."));
        }

        User user = new User(request.getName(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.getPhone(),
                request.getAddress(),
                request.getState(),
                request.getZip());
        userRepository.save(user);

        Token token = new Token(request.getEmail());
        tokenRepository.save(token);

        return ResponseEntity.ok(new MessageResponse("User Registered Successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        String token = helper.extractTokenFromRequest();
        if (token!=null) {
            helper.invalidateToken(token);
            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseEntity.ok(new MessageResponse("Logged Out Successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Failed to logout"));
    }
}
