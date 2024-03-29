package com.example.easypay.modals.dtos.cutomerdtos;

import com.example.easypay.modals.entities.Address;
import com.example.easypay.modals.entities.CustomerRole;
import com.example.easypay.modals.enums.Gender;
import com.example.easypay.modals.enums.Verification;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {


    private UUID customerId;

    private String name;

    private String mobile;


    private Set<Address> addresses;


    private Set<CustomerRole> roles;

    private String email;


    private Gender gender;


    private Verification verificationStatus;

}
