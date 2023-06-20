package com.example.loginapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Dashboard {
    @RequestMapping("/welcome")
    public String welcome(){
        String s = "This is a private page";
        return s;
    }
}
