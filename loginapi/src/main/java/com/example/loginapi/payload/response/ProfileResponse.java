package com.example.loginapi.payload.response;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
    String name;
    String username;
    String email;
    String phone;
    String address;
    String state;
    String zip;

}
