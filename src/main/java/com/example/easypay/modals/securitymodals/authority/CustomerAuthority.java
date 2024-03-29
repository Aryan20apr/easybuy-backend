package com.example.easypay.modals.securitymodals.authority;

import com.example.easypay.modals.entities.CustomerRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class CustomerAuthority implements GrantedAuthority {


    private final CustomerRole customerRole;

    @Override
    public String getAuthority() {
        return customerRole.getRoleName();
    }
}
