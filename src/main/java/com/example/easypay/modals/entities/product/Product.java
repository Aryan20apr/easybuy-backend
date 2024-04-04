package com.example.easypay.modals.entities.product;

import com.example.easypay.modals.entities.category.Category;
import com.example.easypay.modals.entities.seller.Seller;
import com.example.easypay.modals.enums.ProductAvailibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String productName;

    private String count;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String productToken;

    @Enumerated(EnumType.STRING)
    private ProductAvailibility availibility;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="seller_id")
    private Seller seller;

    private int markedPrice;

    private int displayPrice;

    private double discountPercent;

    private int orderLimit;

    private String counntryOfOrigin;

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