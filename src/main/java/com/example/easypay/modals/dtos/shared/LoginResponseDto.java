package com.example.easypay.modals.dtos.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDto {

    private String token;
    private String accessToken;
    private String refreshToken;
    private String username;
}