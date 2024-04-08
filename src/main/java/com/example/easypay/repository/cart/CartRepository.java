package com.example.easypay.repository.cart;

import com.example.easypay.modals.entities.cart.Cart;
import com.example.easypay.modals.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {


    @Query(
            """
        SELECT 
          p.id as productId,   
            p.productName as productName,
                    p.productToken as productToken,
                    p.markedPrice as markedPrice,
                    p.displayPrice as displayPrice,
                    p.discountPercent as discountPercent,
                    p.orderLimit as orderLimit,
                    p.countryOfOrigin as countryOfOrigin,
                    p.seller.companyName as companyName,
                    p.seller.sellerToken as sellerToken
                    
        from Cart c ,Product p WHERE c.id= ?1
"""
    )
    List<ProductProjection> getCartProducts(Long cartId);
}
