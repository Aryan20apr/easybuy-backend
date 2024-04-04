package com.example.easypay.modals.securitymodals.authority;

import com.example.easypay.modals.entities.admins.Admin;
import com.example.easypay.modals.entities.customer.Customer;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SecurityAdmin implements UserDetails
{
    private final Admin admin;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return admin
                .getRoles()
                .stream()
                .map(AdminAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.admin.getPassword();
    }

    @Override
    public String getUsername() {
        return this.admin.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return this.customer.getVerificationStatus()== Verification.VERIFIED;
        return true;
    }
}
