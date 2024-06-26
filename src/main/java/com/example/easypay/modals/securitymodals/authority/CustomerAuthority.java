package com.example.easypay.modals.securitymodals.authority;

import com.example.easypay.modals.entities.customer.CustomerRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class CustomerAuthority implements GrantedAuthority {


    private final CustomerRole customerRole;

    @Override
    public String getAuthority() {
        return customerRole.getRoleName();
    }
}
