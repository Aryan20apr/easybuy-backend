package com.example.easypay.config.security.services;

import com.example.easypay.modals.entities.Customer;
import com.example.easypay.modals.securitymodals.SecurityCustomer;
import com.example.easypay.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> user = customerRepository.findByEmail(username);

        return user.map(SecurityCustomer::new).orElse(null);
    }
}

