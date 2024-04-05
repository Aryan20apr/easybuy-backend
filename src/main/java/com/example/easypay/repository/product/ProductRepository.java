package com.example.easypay.repository.product;

import com.example.easypay.modals.entities.product.Product;
import com.example.easypay.modals.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.easypay.modals.enums.ProductAvailibility;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("""
            SELECT  p.productName,
                    p.productToken,
                    p.markedPrice,
                    p.displayPrice,
                    p.discountPercent,
                    p.orderLimit,
                    p.counntryOfOrigin,
                    p.seller.companyName,
                    p.images
             from Product p  WHERE p.productToken=?1
            """)
    public ProductProjection getProductByToken(String token);



    @Query("""
            SELECT  p.productName,
                    p.productToken,
                    p.markedPrice,
                    p.displayPrice,
                    p.discountPercent,
                    p.orderLimit,
                    p.counntryOfOrigin,
                    p.seller.companyName
             from Product p  WHERE p.availibility="AVAILABLE" AND p.count>0 AND p.category.id=?1
            """)
    public ProductProjection getProductsByCategory(Long id);

    @Query("""
            SELECT  p.productName,
                    p.productToken,
                    p.markedPrice,
                    p.displayPrice,
                    p.discountPercent,
                    p.orderLimit,
                    p.counntryOfOrigin,
                    p.seller.companyName
             from Product p  WHERE p.availibility="AVAILABLE" AND p.count>0 AND p.category.id=?1
            """)
    public List<ProductProjection> findAllProduct();

}
