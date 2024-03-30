package com.example.easypay.repository;

import com.example.easypay.modals.entities.CustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRoleRespository extends JpaRepository<CustomerRole,Long> {

    @Query("SELECT u FROM CustomerRole  u WHERE u.roleName = ?1")
    Optional<CustomerRole> findByRoleName(String roleName);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

}
