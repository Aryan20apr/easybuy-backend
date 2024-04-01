package com.example.easypay.modals.entities.seller;

import com.example.easypay.modals.entities.Address;
import com.example.easypay.modals.entities.CustomerRole;
import com.example.easypay.modals.enums.Gender;
import com.example.easypay.modals.enums.Verification;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="seller")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "seller_token", nullable = false, unique = true)
    private String sellerToken;

    private String companyName;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<ContactDetail>  contactDetails=new HashSet<>();

    private String email;

    private String password;




    @Enumerated(EnumType.STRING)
    private Verification verificationStatus;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void generateToken() {
        if (this.sellerToken == null) {
            this.sellerToken = UUID.randomUUID().toString();
        }
    }


}
