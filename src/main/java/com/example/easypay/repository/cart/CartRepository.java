package com.example.easypay.repository.cart;

import com.example.easypay.modals.entities.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {


}
