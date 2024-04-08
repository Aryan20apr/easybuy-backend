package com.example.easypay.controllers.Customer;

import com.example.easypay.modals.dtos.shared.ApiResponse;
import com.example.easypay.modals.projections.ProductProjection;
import com.example.easypay.repository.product.ProductRepository;
import com.example.easypay.services.serviceimpl.cart.CartServiceImpl;
import com.example.easypay.services.serviceimpl.product.ProductServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/easybuy/api/v1/cart")
@AllArgsConstructor
@Slf4j
public class CartController {


    private CartServiceImpl cartService;

    private ProductServiceImpl productService;


    @PutMapping("/add")
    public ResponseEntity<ApiResponse<String>> addProduct(@RequestParam Long cartId,@RequestParam String productToken)
    {
        cartService.addToCart(cartId,productToken);

        return new ResponseEntity<>(new ApiResponse<>("Product added successfully to cart"), HttpStatus.CREATED);
    }

    @PutMapping("/remove")
    public ResponseEntity<ApiResponse<String>> removeProduct(@RequestParam Long cartId,@RequestParam String productToken)
    {
        cartService.removeFromCart(cartId,productToken);

        return new ResponseEntity<>(new ApiResponse<>("Product removed from cart successfully."), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductProjection>>> cartItems(@RequestParam String customerToken)
    {
        return new ResponseEntity<>(new ApiResponse<>(productService.getCartProducts(customerToken)),HttpStatus.OK);
    }

}
