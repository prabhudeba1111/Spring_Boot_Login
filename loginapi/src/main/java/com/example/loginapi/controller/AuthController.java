package com.example.loginapi.controller;

import com.example.loginapi.entity.User;
import com.example.loginapi.payload.request.LoginRequest;
import com.example.loginapi.payload.response.JwtResponse;
import com.example.loginapi.payload.request.SignupRequest;
import com.example.loginapi.payload.response.MessageResponse;
import com.example.loginapi.repository.UserRepository;
import com.example.loginapi.security.JwtHelper;
import com.example.loginapi.service.UserDetailsServiceImpl;
import com.example.loginapi.service.UserVerify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class AuthController extends User {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserVerify userVerify;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        System.out.println("requesting");
//        this.doAuthenticate(request.getEmail(), request.getPassword());
        if (!userVerify.authenticate(request.getEmail(), request.getPassword())){
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
        System.out.println("User Verified");
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(request.getEmail());
        System.out.println("User Created");
        String token = this.helper.generateToken(userDetails);
        System.out.println("Token Generated");
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request){
        System.out.println("start");
        if (userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Email already in use."));
        }
        System.out.println("eamil ok");
        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("Passwords don't match."));
        }
        System.out.println("password match");

        User user = new User(request.getName(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.getPhone(),
                request.getAddress(),
                request.getState(),
                request.getZip());

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Registered Successfully"));
    }

//    private void doAuthenticate(String email, String password) {
//
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
//        try {
//            manager.authenticate(authentication);
//        }
//        catch (BadCredentialsException e) {
//            throw new BadCredentialsException(" Invalid Username or Password  !!");
//        }
//    }

}
