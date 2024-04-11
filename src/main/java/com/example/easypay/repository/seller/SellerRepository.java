package com.example.easypay.repository.seller;

import com.example.easypay.modals.projections.SellerDetailsProjection;
import com.example.easypay.modals.entities.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("select c from Seller c where c.email = ?1")
    Optional<Seller> findByEmail(String email);

    @Query("select s.sellerToken from Seller s where s.email = ?1")
    String findTokenByEmail(String email);


    @Query("select s from Seller s where s.sellerToken = ?1")
    Optional<Seller> findBySellerToken(String sellerToken);

    Boolean existsByEmail(String email);


    @Query("""
            SELECT
            s.sellerToken as sellerToken,
            s.companyName as company,
            s.email as email,
            s.userVerificationStatus as verificationStatus
            FROM
            Seller s WHERE s.sellerToken = ?1
            """)
    SellerDetailsProjection getSellerDetails(String token);


}