package com.example.easypay.modals.dtos.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordDto {

    private String oldPassword;
    private String newPassword;
    private String consumerToken;
}