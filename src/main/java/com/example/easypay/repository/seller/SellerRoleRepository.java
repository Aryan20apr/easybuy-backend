package com.example.easypay.repository.seller;


import com.example.easypay.modals.entities.seller.SellerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SellerRoleRepository extends JpaRepository<SellerRole, Long> {

    @Query("SELECT u FROM SellerRole  u WHERE u.roleName = ?1")
    Optional<SellerRole> findByRoleName(String roleName);
}