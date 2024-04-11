package com.example.easypay.controllers;


import com.example.easypay.modals.dtos.product.ProductDto;
import com.example.easypay.modals.dtos.shared.ApiResponse;
import com.example.easypay.modals.projections.ProductProjection;
import com.example.easypay.repository.product.ProductRepository;
import com.example.easypay.services.interfaces.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/easybuy/api/v1/product")
@AllArgsConstructor
public class ProductController {



    private ProductService productService;
    @PostMapping
    ResponseEntity<ApiResponse<String>> createProduct(@RequestBody ProductDto productDto)
    {
        return new ResponseEntity<>(new ApiResponse<>(productService.createProduct(productDto)), HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<ApiResponse<String>> updateProduct(@RequestBody ProductDto productDto)
    {
        productService.updateProduct(productDto);
        return new ResponseEntity<>(new ApiResponse<>("Product updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<ApiResponse<String>> deleteProduct(@RequestParam String token)
    {
        productService.removeProduct(token);
        return new ResponseEntity<>(new ApiResponse<>("Product deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<ApiResponse<List<ProductProjection>>> getAllProducts(@RequestParam Long categoryId)
    {

        return new ResponseEntity<>(new ApiResponse<>(productService.getAllProductsByCategory(categoryId)), HttpStatus.OK
        );
    }

    @GetMapping
    ResponseEntity<ApiResponse<ProductProjection>> getSingleProduct(@RequestParam String productToken)
    {
         //productService.findProductById(1L);

        return new ResponseEntity<>(new ApiResponse<>(productService.getProductByToken(productToken)), HttpStatus.OK);
    }


}
