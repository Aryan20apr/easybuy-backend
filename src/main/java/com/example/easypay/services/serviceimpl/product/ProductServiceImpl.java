package com.example.easypay.services.serviceimpl.product;

import com.example.easypay.modals.dtos.product.ProductDto;
import com.example.easypay.modals.entities.category.Category;
import com.example.easypay.modals.entities.product.Product;
import com.example.easypay.modals.entities.seller.Seller;
import com.example.easypay.modals.enums.ProductAvailibility;
import com.example.easypay.modals.projections.ProductProjection;
import com.example.easypay.repository.category.CategoryRepository;
import com.example.easypay.repository.product.ProductRepository;
import com.example.easypay.repository.seller.SellerRepository;
import com.example.easypay.services.interfaces.product.ProductService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {



    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private CategoryRepository categoryRepository;
    @Override
    public String createProduct(ProductDto productDto) {

        String sellerToken=productDto.getSellerToken();
        Long categoryId=productDto.getCategoryId();
        Optional<Seller> seller=sellerRepository.findBySellerToken(sellerToken);
        Optional<Category> category=categoryRepository.findById(productDto.getCategoryId());

        Product product= Product.builder()
                .productName(productDto.getProductName())
                .count(productDto.getCount())
                .availibility(productDto.getAvailibility()? ProductAvailibility.AVAILABLE:ProductAvailibility.OUT_OF_STOCK)
                .counntryOfOrigin(productDto.getCounntryOfOrigin())
                .displayPrice(productDto.getDisplayPrice())
                .markedPrice(productDto.getMarkedPrice())
                .discountPercent(productDto.getDiscountPercent())
                .orderLimit(productDto.getOrderLimit())
                .build();

        if(!seller.isPresent())
        {
            throw new ApiException("Seller with the token "+sellerToken+" does not exist");
        }
        else
        {
            seller.get().addProduct(product);
        }
        if(!category.isPresent())
        {
            throw new ApiException("Category with id "+categoryId+" does not exist");
        }
        else
        {
            category.get().addProduct(product);
        }
        Product savedproduct=productRepository.save(product);

        return savedproduct.getProductToken();



    }

    @Override
    public String updateProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductProjection getProductByToken(String token) {
        ProductProjection product= productRepository.getProductByToken(token);
        return product;
    }

    @Override
    public List<ProductProjection> getAllProducts() {
        return productRepository.findAllProduct();
    }

    @Override
    public void removeProduct(String token) {

    }
}
