package com.example.easypay.services.interfaces.product;

import com.example.easypay.modals.dtos.product.ProductDto;
import com.example.easypay.modals.projections.ProductProjection;

import java.util.List;

public interface ProductService {


    public void findProductById(Long id);
    public String createProduct(ProductDto productDto);

    public void updateProduct(ProductDto productDto);


    public List<ProductProjection> getAllProductsByCategory(Long id);

    public void removeProduct(String token);

    public ProductProjection getProductByToken(String token);

}
