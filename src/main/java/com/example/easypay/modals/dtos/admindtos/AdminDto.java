package com.example.easypay.modals.dtos.admindtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AdminDto {

    private String password;


    private Set<String> roles;

    private String email;

}
