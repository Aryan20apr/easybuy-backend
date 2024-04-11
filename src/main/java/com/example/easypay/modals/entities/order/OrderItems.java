package com.example.easypay.modals.entities.order;

import com.example.easypay.modals.entities.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import java.util.UUID;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

//    @ManyToMany(mappedBy = "orderItems", fetch = FetchType.LAZY)
//    @JoinTable(name = "orders_products", joinColumns = @JoinColumn(name = "order_item_id", referencedColumnName = "order_item_id"), inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"))
//    private Set<Product> products;
//
//    @Column(name = "order_count")
//    private int count;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;

    @Column(name = "order_item_count")
    private int count;

    private Long subTotal;

    private Long subTotalDiscount;


    private String order_item_token;

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
        if (this.order_item_token == null) {
            this.order_item_token = UUID.randomUUID().toString();
        }
    }


}
