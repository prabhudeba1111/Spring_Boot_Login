package com.example.loginapi.service;

import com.example.loginapi.entity.User;
import com.example.loginapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public interface UserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}