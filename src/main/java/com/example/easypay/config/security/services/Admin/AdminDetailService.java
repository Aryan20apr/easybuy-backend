package com.example.easypay.config.security.services.Admin;

import com.example.easypay.modals.entities.admins.Admin;
import com.example.easypay.modals.securitymodals.authority.SecurityAdmin;
import com.example.easypay.repository.admin.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class AdminDetailService implements UserDetailsService {


        private final AdminRepository adminRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<Admin> user = adminRepository.findByEmail(username);

            return user.map(SecurityAdmin::new).orElse(null);
        }
    }

