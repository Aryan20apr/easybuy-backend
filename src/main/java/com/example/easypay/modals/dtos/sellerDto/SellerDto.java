package com.example.easypay.modals.dtos.sellerDto;

import com.example.easypay.modals.enums.UserVerification;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDto {

    private String companyName;

    private Set<ContactDetailDto>  contactDetails=new HashSet<>();

    private String email;

    private String password;

    private Set<String> roles;

    private UserVerification userVerificationStatus;

}
