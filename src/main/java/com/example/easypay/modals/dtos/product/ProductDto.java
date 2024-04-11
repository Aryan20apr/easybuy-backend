package com.example.easypay.modals.dtos.product;

import com.example.easypay.modals.entities.category.Category;
import com.example.easypay.modals.entities.seller.Seller;
import com.example.easypay.modals.enums.ProductAvailibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productName;

    private int count;

    private Long categoryId;


    private Boolean availibility;

    private String sellerToken;

    private int markedPrice;

    private int displayPrice;

    private double discountPercent;

    private int orderLimit;

    private String description;

    private String counntryOfOrigin;

    private List<String> imageURLs;




}
