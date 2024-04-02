package com.example.easypay.modals.entities.customer;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<Customer> customers=new HashSet<>();


    public void addCustomer(Customer customer)
    {
        this.customers.add(customer);
        customer.getRoles().add(this);
    }

    public void removeCustomer(Customer customer)
    {
        this.customers.remove(customer);
        customer.getRoles().remove(this);
    }
}
