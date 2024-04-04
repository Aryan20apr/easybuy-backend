package com.example.easypay.modals.securitymodals.authority;

import com.example.easypay.modals.entities.admins.AdminRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class AdminAuthority implements GrantedAuthority {


    private final AdminRole adminRole;

    @Override
    public String getAuthority() {
        return adminRole.getRoleName();
    }
}

