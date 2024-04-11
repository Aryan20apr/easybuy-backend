package com.example.easypay.modals.dtos.cutomerdtos;

import com.example.easypay.modals.enums.Gender;
import com.example.easypay.modals.enums.UserVerification;
import lombok.*;

import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {




    private String name;

    private String mobile;

    private String password;

    private AddressDto address;


    private Set<String> roles;

    private String email;


    private Gender gender;


    private UserVerification userVerificationStatus;

}
