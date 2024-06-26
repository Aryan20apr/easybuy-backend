package com.example.easypay.modals.entities.customer;

import com.example.easypay.modals.entities.cart.Cart;
import com.example.easypay.modals.enums.Gender;

import com.example.easypay.modals.enums.UserVerification;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Entity
@Table(name="customer")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_token", nullable = false, unique = true)
    private String customerToken;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "mobile",nullable = false)
    private String mobile;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REMOVE})
    @JoinTable(name = "customer_address", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"), inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "address_id"))
    private Set<Address> addresses=new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_roles", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<CustomerRole> roles=new HashSet<>();

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Enumerated(EnumType.STRING)
    private UserVerification userVerificationStatus;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

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
        if (this.customerToken == null) {
            this.customerToken = UUID.randomUUID().toString();
        }
    }

    public void addRole(CustomerRole role)
    {

        this.roles.add(role);
        role.getCustomers().add(this);
    }

    private void removeRole(CustomerRole role)
    {
        this.roles.remove(role);
        role.getCustomers().remove(this);
    }

    public void addAddress(Address address)
    {
        this.addresses.add(address);
        //address.getCustomers().add(this);
    }

    public void removeAddress(Address address)
    {
        this.addresses.remove(address);
        address.getCustomers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(getCustomerId(), customer.getCustomerId()) && Objects.equals(getCustomerToken(), customer.getCustomerToken()) && Objects.equals(getName(), customer.getName()) && Objects.equals(getMobile(), customer.getMobile()) && Objects.equals(getAddresses(), customer.getAddresses()) && Objects.equals(getRoles(), customer.getRoles()) && Objects.equals(getEmail(), customer.getEmail()) && Objects.equals(getPassword(), customer.getPassword()) && getGender() == customer.getGender() && getUserVerificationStatus() == customer.getUserVerificationStatus() && Objects.equals(getCreatedAt(), customer.getCreatedAt()) && Objects.equals(getUpdatedAt(), customer.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getCustomerToken(), getName(), getMobile(), getAddresses(), getRoles(), getEmail(), getPassword(), getGender(), getUserVerificationStatus(), getCreatedAt(), getUpdatedAt());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerToken='" + customerToken + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addresses=" + addresses +
                ", roles=" + roles +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", verificationStatus=" + userVerificationStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
