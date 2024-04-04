package com.example.easypay.repository.admin;


import com.example.easypay.modals.entities.admins.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("select c from Admin c where c.email = ?1")
    Optional<Admin> findByEmail(String email);

    @Query("select c.adminToken from Admin c where c.email = ?1")
    String findTokenByEmail(String email);


    @Query("select c from Admin c where c.adminToken = ?1")
    Optional<Admin> findByAdminToken(String consumerToken);

    Boolean existsByEmail(String email);




}