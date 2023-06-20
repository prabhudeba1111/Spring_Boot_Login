package com.example.loginapi.controller;

import com.example.loginapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @Autowired
    UserDetailsService userDetailsService;

    @RequestMapping("/welcome")
    public String welcome(){
        String s = "This is a private page";
        return s;
    }
}





