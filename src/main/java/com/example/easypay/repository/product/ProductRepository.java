package com.example.easypay.repository.product;

import com.example.easypay.modals.entities.product.Product;
import com.example.easypay.modals.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {



    @Query("""
        SELECT COUNT(*) FROM Product p WHERE p.productToken = ?1

""")
    public Integer checkIfProductExists(String token);


    @Query("""
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
                    
                                                
             from Product p  WHERE p.productToken= ?1
            """)
    public ProductProjection getProductByToken(String token);

    public Product findByProductToken(String token);



    @Query("""
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
                    
             from Product p  WHERE  p.count>0 AND p.category.id=?1
            """)
    public ProductProjection getProductsByCategory(Long id);

    @Query("""
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
             from Product p  WHERE p.count>0 AND p.category.id=?1
            """)
    public List<ProductProjection> findAllProduct(Long id);

    @Modifying
    @Query("""
DELETE FROM Product p WHERE p.productToken=?1
""")
    public void removeByToken(String token);

    @Query("""
            SELECT 
            p.id as productId, 
            p.productName as productName, 
            p.productToken as productToken, 
            p.markedPrice as markedPrice, 
            p.displayPrice as displayPrice, 
            p.discountPercent as discountPercent, 
            p.orderLimit as orderLimit, 
            p.countryOfOrigin as countryOfOrigin, 
            s.companyName as companyName, 
            s.sellerToken as sellerToken 
            FROM Cart c JOIN c.cartItems p JOIN p.seller s WHERE c.id = :cartId """)
    List<ProductProjection> findProductsByCartId(@Param("cartId") Long cartId);



}
