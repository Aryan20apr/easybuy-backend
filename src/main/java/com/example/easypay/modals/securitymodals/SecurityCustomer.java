package com.example.easypay.modals.securitymodals;

import com.example.easypay.modals.entities.Customer;
import com.example.easypay.modals.enums.Verification;
import com.example.easypay.modals.securitymodals.authority.CustomerAuthority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SecurityCustomer implements UserDetails {

    private final Customer customer;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customer
                .getRoles()
                .stream()
                .map(CustomerAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.customer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.customer.getEmail();
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
        return this.customer.getVerificationStatus()== Verification.VERIFIED;
    }
}
