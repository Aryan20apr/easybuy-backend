package com.example.easypay.modals.entities.seller;



import com.example.easypay.modals.entities.customer.Address;
import com.example.easypay.modals.enums.Verification;
import com.example.easypay.modals.entities.seller.SellerRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

import static com.example.easypay.utils.AppConstants.ENTITY_TYPE_SELLER;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "seller_roles", joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "seller_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
   private HashSet<SellerRole> roles=new HashSet<SellerRole>();

    private String email;

    private String password;


    private final String role=ENTITY_TYPE_SELLER;


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
    public void addContact(ContactDetail contact)
    {
        this.contactDetails.add(contact);

    }

    public void removeContact(ContactDetail contactDetail)
    {
        this.contactDetails.remove(contactDetail);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller seller)) return false;
        return Objects.equals(getSellerId(), seller.getSellerId()) && Objects.equals(getSellerToken(), seller.getSellerToken()) && Objects.equals(getCompanyName(), seller.getCompanyName()) && Objects.equals(getContactDetails(), seller.getContactDetails()) && Objects.equals(getEmail(), seller.getEmail()) && Objects.equals(getPassword(), seller.getPassword()) && getVerificationStatus() == seller.getVerificationStatus() && Objects.equals(getCreatedAt(), seller.getCreatedAt()) && Objects.equals(getUpdatedAt(), seller.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellerId(), getSellerToken(), getCompanyName(), getContactDetails(), getEmail(), getPassword(), getVerificationStatus(), getCreatedAt(), getUpdatedAt());
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", sellerToken='" + sellerToken + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactDetails=" + contactDetails +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verificationStatus=" + verificationStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
