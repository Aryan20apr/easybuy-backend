package com.example.easypay.modals.entities.cart;

import com.example.easypay.modals.entities.customer.Customer;
import com.example.easypay.modals.entities.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
//    @PrimaryKeyJoinColumn(name = "customer_id",referencedColumnName = "customer_id")
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"))
    private Set<Product> cartItems=new HashSet<>();

    private int cartTotal;



    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public void addProduct(Product product)
    {
        this.cartItems.add(product);
    }




}