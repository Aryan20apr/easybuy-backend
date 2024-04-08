package com.example.easypay.modals.projections;


import java.util.List;

public interface ProductProjection {

    public Long getProductId();

   public String getProductName();

   public String getProductToken();

   public Integer getMarkedPrice();

   public Integer getDisplayPrice();

   public  Double getDiscountPercent();

    Integer getOrderLimit();

    String getCountryOfOrigin();

    String getCompanyName();

    String getSellerToken();

}
