package com.example.easypay.modals.entities.seller;



import com.example.easypay.modals.entities.product.Product;
import com.example.easypay.modals.enums.UserVerification;
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

    @Column(name = "companyName",nullable = false,unique = true)
    private String companyName;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<ContactDetail>  contactDetails=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "seller_roles", joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "seller_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
   private Set<SellerRole> roles=new HashSet<SellerRole>();

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    private final String role=ENTITY_TYPE_SELLER;


    @Enumerated(EnumType.STRING)
    private UserVerification userVerificationStatus;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<Product> products;

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
        //contact.setSeller(this);

    }

    public void removeContact(ContactDetail contactDetail)
    {
        this.contactDetails.remove(contactDetail);


    }

    public void addProduct(Product product)
    {
        this.products.add(product);
        product.setSeller(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller seller)) return false;
        return Objects.equals(getSellerId(), seller.getSellerId()) && Objects.equals(getSellerToken(), seller.getSellerToken()) && Objects.equals(getCompanyName(), seller.getCompanyName()) && Objects.equals(getEmail(), seller.getEmail()) && Objects.equals(getPassword(), seller.getPassword()) && Objects.equals(getRole(), seller.getRole()) && getUserVerificationStatus() == seller.getUserVerificationStatus() && Objects.equals(getCreatedAt(), seller.getCreatedAt()) && Objects.equals(getUpdatedAt(), seller.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellerId(), getSellerToken(), getCompanyName(), getEmail(), getPassword(), getRole(), getUserVerificationStatus(), getCreatedAt(), getUpdatedAt());
    }
}
