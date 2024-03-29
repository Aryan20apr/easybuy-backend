package com.example.easypay.repository;

import com.example.easypay.modals.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("select c from Customer c where c.email = ?1")
    Optional<Customer> finfByEmail(String email);

}
