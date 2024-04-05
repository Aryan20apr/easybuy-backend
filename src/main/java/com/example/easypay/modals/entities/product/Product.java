package com.example.easypay.modals.entities.product;

import com.example.easypay.modals.dtos.product.ProductImages;
import com.example.easypay.modals.entities.category.Category;
import com.example.easypay.modals.entities.order.OrderItems;
import com.example.easypay.modals.entities.seller.Seller;
import com.example.easypay.modals.enums.ProductAvailibility;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    private String productName;

    private int count;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name = "category_id")
    private Category category;

    private String productToken;

    @Enumerated(EnumType.STRING)
    private ProductAvailibility availibility;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name="seller_id")
    private Seller seller;

    private int markedPrice;

    private int displayPrice;

    private double discountPercent;

    private int orderLimit;

    private String counntryOfOrigin;


    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private List<ProductImages> images=new ArrayList<>();

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,
//            CascadeType.PERSIST, CascadeType.DETACH,CascadeType.REMOVE})
//    private Set<OrderItems> orderItems=new HashSet<>();

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
        if (this.productToken == null) {
            this.productToken = UUID.randomUUID().toString();
        }
    }

}