package com.example.easypay.services.interfaces.product;

import com.example.easypay.modals.dtos.product.ProductDto;
import com.example.easypay.modals.projections.ProductProjection;

import java.util.List;

public interface ProductService {


    public String createProduct(ProductDto productDto);

    public String updateProduct(ProductDto productDto);


    public List<ProductProjection> getAllProducts();

    public void removeProduct(String token);

    public ProductProjection getProductByToken(String token);

}
