package com.example.easypay.modals.projections;


import java.util.List;

public interface ProductProjection {

    String getProductName();

    String getProductToken();

    int getMarkedPrice();

    int getDisplayPrice();

    int getDiscountPercent();

    int getOrderLimit();

    String getCounntryOfOrigin();

    String getCompanyName();

    List<String> getImages();


}
