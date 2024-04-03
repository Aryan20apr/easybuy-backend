package com.example.easypay.modals.dtos.sellerDto;

import com.example.easypay.modals.entities.seller.ContactDetail;
import com.example.easypay.modals.entities.seller.SellerRole;
import com.example.easypay.modals.enums.Verification;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static com.example.easypay.utils.AppConstants.ENTITY_TYPE_SELLER;

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

    private Verification verificationStatus;

}
