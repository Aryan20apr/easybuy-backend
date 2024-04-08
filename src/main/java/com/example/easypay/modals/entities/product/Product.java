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

    private String countryOfOrigin;


    @OneToMany(fetch = FetchType.LAZY,orphanRemoval = true,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
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

    public void addImages(List<ProductImages> productImages)
    {

        images.addAll(productImages);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getCount() == product.getCount() && getMarkedPrice() == product.getMarkedPrice() && getDisplayPrice() == product.getDisplayPrice() && Double.compare(getDiscountPercent(), product.getDiscountPercent()) == 0 && getOrderLimit() == product.getOrderLimit() && Objects.equals(getId(), product.getId()) && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getCategory(), product.getCategory()) && Objects.equals(getProductToken(), product.getProductToken()) && getAvailibility() == product.getAvailibility() && Objects.equals(getSeller(), product.getSeller()) && Objects.equals(getCountryOfOrigin(), product.getCountryOfOrigin()) && Objects.equals(getImages(), product.getImages()) && Objects.equals(getCreatedAt(), product.getCreatedAt()) && Objects.equals(getUpdatedAt(), product.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductName(), getCount(), getCategory(), getProductToken(), getAvailibility(), getSeller(), getMarkedPrice(), getDisplayPrice(), getDiscountPercent(), getOrderLimit(), getCountryOfOrigin(), getImages(), getCreatedAt(), getUpdatedAt());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", count=" + count +
                ", category=" + category +
                ", productToken='" + productToken + '\'' +
                ", availability=" + availibility +
                ", seller=" + seller +
                ", markedPrice=" + markedPrice +
                ", displayPrice=" + displayPrice +
                ", discountPercent=" + discountPercent +
                ", orderLimit=" + orderLimit +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", images=" + images +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
