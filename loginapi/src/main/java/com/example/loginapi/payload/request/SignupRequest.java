package com.example.loginapi.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    private String name;
    private String phone;
    private String address;
    private String state;
    private String zip;
    private String email;
    private String password;
    private String confirmPassword;
}
