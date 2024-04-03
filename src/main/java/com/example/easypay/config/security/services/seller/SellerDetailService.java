package com.example.easypay.config.security.services.seller;


import com.example.easypay.modals.entities.seller.Seller;

import com.example.easypay.modals.securitymodals.SecuritySeller;

import com.example.easypay.repository.seller.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class SellerDetailService implements UserDetailsService {

    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<Seller> seller = sellerRepository.findByEmail(username);

        return seller.map(SecuritySeller::new).orElse(null);
    }
}