package com.example.easypay.services.interfaces.cart;

public interface CartService {

    public void addToCart(Long cartId,String productToken);

    public void removeFromCart(Long cartId,String productToken);



}
