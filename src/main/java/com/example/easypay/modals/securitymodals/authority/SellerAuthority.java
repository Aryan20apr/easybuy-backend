package com.example.easypay.modals.securitymodals.authority;

import com.example.easypay.modals.entities.seller.SellerRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SellerAuthority implements GrantedAuthority {


    private final SellerRole sellerRole;

    @Override
    public String getAuthority() {
        return sellerRole.getRoleName();
    }
}

