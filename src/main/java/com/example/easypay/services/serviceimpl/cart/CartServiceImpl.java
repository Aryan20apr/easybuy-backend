package com.example.easypay.services.serviceimpl.cart;

import com.example.easypay.modals.entities.cart.Cart;
import com.example.easypay.modals.entities.product.Product;
import com.example.easypay.modals.projections.ProductProjection;
import com.example.easypay.repository.cart.CartRepository;
import com.example.easypay.repository.product.ProductRepository;
import com.example.easypay.services.interfaces.cart.CartService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    @Override
    public void addToCart(Long cartId,String productToken) {


        Optional<Cart> optionalCart= cartRepository.findById(cartId);
        Product product= productRepository.findByProductToken(productToken);
        if(!optionalCart.isPresent())
        {
            log.error("Cart with id "+cartId+" does not exist");
            throw new ApiException("Could not add product to cart");
        }
        else
        {

            Cart cart=optionalCart.get();

            cart.addProduct(product);
            cart.setCartTotal(cart.getCartTotal()+product.getDisplayPrice());
            cartRepository.save(cart);
        }


    }

    @Override
    public void removeFromCart(Long cartid,String productToken) {

        Optional<Cart> cart= cartRepository.findById(cartid);
        Product product= productRepository.findByProductToken(productToken);
        cart.get().setCartTotal(cart.get().getCartTotal()-product.getDisplayPrice());
        cart.get().getCartItems().remove(product);
        cartRepository.save(cart.get());
    }

//    @Override
//    public List<ProductProjection> getCartItems(Long cartId) {
//
//        cartRepository.findById(cartId);
//    }
}
