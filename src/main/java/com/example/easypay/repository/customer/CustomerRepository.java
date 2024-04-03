package com.example.easypay.repository.customer;

import com.example.easypay.modals.dtos.projections.CustomerDetailsProjection;
import com.example.easypay.modals.entities.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("select c from Customer c where c.email = ?1")
    Optional<Customer> findByEmail(String email);

    @Query("select c.customerToken from Customer c where c.email = ?1")
    String findTokenByEmail(String email);


    @Query("select c from Customer c where c.customerToken = ?1")
    Optional<Customer> findByConsumerToken(String consumerToken);

    Boolean existsByEmail(String email);

    Boolean existsBymobile(String phone);

    @Query("""
            SELECT
            c.customerToken as customerToken,
            c.name as name,
            c.email as email,
            c.mobile as mobile,
            c.gender as gender,
            c.verificationStatus as verificationStatus
            FROM
            Customer c WHERE c.customerToken = ?1
            """)
    CustomerDetailsProjection getCustomerDetails(String token);

}
