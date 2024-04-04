package com.example.easypay.repository.admin;

import com.example.easypay.modals.entities.admins.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRolesRepository extends JpaRepository<AdminRole, Long> {

    @Query("SELECT u FROM AdminRole u WHERE u.roleName = ?1")
    Optional<AdminRole> findByRoleName(String roleName);
}