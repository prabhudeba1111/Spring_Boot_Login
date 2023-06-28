package com.example.loginapi.controller;

import com.example.loginapi.payload.request.CommentRequest;
import com.example.loginapi.payload.request.PostRequest;
import com.example.loginapi.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Dashboard {
    @RequestMapping("/welcome")
    public String welcome(Principal principal) {
        return "Welcome " + principal.getName() + " .";
    }

    @RequestMapping("/post")
    public ResponseEntity<?> post(@RequestBody PostRequest request){

        return ResponseEntity.ok(new MessageResponse("ok"));
    }

    @RequestMapping("/comment")
    public ResponseEntity<?> comment(@RequestBody CommentRequest request){

        return ResponseEntity.ok(new MessageResponse("ok"));
    }
}
