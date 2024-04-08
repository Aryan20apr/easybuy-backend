package com.example.easypay.services.interfaces.cart;

import com.example.easypay.modals.projections.ProductProjection;

import java.util.List;

public interface CartService {

    public void addToCart(Long cartId,String productToken);

    public void removeFromCart(Long cartId,String productToken);

    //public List<ProductProjection> getCartItems(Long cartId);

}
