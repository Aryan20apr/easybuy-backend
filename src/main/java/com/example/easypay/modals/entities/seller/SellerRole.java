package com.example.easypay.modals.entities.seller;


import com.example.easypay.modals.entities.customer.Customer;
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
public class SellerRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<Seller> sellers=new HashSet<>();


    public void addSeller(Seller seller)
    {
        this.sellers.add(seller);
        seller.getRoles().add(this);
    }

    public void removeSeller(Seller seller)
    {
        this.sellers.remove(seller);
        seller.getRoles().remove(this);
    }
}
