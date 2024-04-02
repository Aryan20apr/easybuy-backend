package com.example.easypay.modals.securitymodals;

import com.example.easypay.modals.entities.customer.Customer;
import com.example.easypay.modals.entities.seller.Seller;
import com.example.easypay.modals.securitymodals.authority.CustomerAuthority;
import com.example.easypay.modals.securitymodals.authority.SellerAuthority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SecuritySeller implements UserDetails {

    private final Seller seller;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return seller

                .getRoles()
                .stream()
                .map(SellerAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.seller.getPassword();
    }

    @Override
    public String getUsername() {
        return this.seller.getEmail();
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

