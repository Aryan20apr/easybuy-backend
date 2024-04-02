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


    private Set<ContactDetail>  contactDetails=new HashSet<>();


    private HashSet<String> roles=new HashSet<>();

    private String email;

    private String password;


    private final String role=ENTITY_TYPE_SELLER;



    private Verification verificationStatus;




}
