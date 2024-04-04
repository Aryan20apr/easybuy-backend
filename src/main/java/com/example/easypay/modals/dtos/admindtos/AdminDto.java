package com.example.easypay.modals.dtos.admindtos;

import com.example.easypay.modals.dtos.cutomerdtos.AddressDto;
import com.example.easypay.modals.enums.Gender;
import com.example.easypay.modals.enums.Verification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

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
