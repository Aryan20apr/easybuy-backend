package com.example.easypay.services.serviceimpl.product;

import com.example.easypay.modals.dtos.product.ProductDto;
import com.example.easypay.modals.dtos.product.ProductImages;
import com.example.easypay.modals.entities.category.Category;
import com.example.easypay.modals.entities.product.Product;
import com.example.easypay.modals.entities.seller.Seller;
import com.example.easypay.modals.enums.ProductAvailibility;
import com.example.easypay.modals.projections.ProductProjection;
import com.example.easypay.repository.cart.CartRepository;
import com.example.easypay.repository.category.CategoryRepository;
import com.example.easypay.repository.customer.CustomerRepository;
import com.example.easypay.repository.product.ProductRepository;
import com.example.easypay.repository.seller.SellerRepository;
import com.example.easypay.services.interfaces.product.ProductService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {



    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private CartRepository cartRepository;


    public void findProductById(Long id)
    {
    Optional<Product> product= productRepository.findById(id);
        System.out.println(product);
    }
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
                .countryOfOrigin(productDto.getCounntryOfOrigin())
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



       List<ProductImages> productImages= productDto
               .getImageURLs()
               .stream()
               .map(url-> ProductImages.builder().imageUrl(url).build())
               .collect(Collectors.toList());
        //product.setImages(productImages);
        //product.addImages(productImages);


        Product savedproduct=productRepository.save(product);
        product.setImages(productImages);
        productRepository.save(product);
        return savedproduct.getProductToken();



    }

    @Override
    public void updateProduct(ProductDto productDto) {
        Product product=productRepository.findByProductToken(productDto.getSellerToken());
        product.setProductName(productDto.getProductName());
        product.setAvailibility(productDto.getAvailibility()?ProductAvailibility.AVAILABLE:ProductAvailibility.OUT_OF_STOCK);
        product.setCountryOfOrigin(productDto.getCounntryOfOrigin());
        product.setDiscountPercent(productDto.getDiscountPercent());
        product.setDisplayPrice(productDto.getDisplayPrice());
        product.setMarkedPrice(productDto.getMarkedPrice());
        product.setCount(productDto.getCount());
        product.setOrderLimit(productDto.getOrderLimit());

        productRepository.save(product);
    }

    @Override
    public ProductProjection getProductByToken(String token) {
        log.info("Count of product with productTojken obtained is: "+productRepository.checkIfProductExists(token));
        ProductProjection product= productRepository.getProductByToken(token);

        log.info("Prodct obtained is : "+product.getProductName());

        return product;
    }

    @Override
    public List<ProductProjection> getCartProducts(String customerToken) {

        Long customerCartId= customerRepository.findCustomerCartId(customerToken);



        List<ProductProjection> cartItems= productRepository.findProductsByCartId(customerCartId);
        return cartItems;


  }

    @Override
    public List<ProductProjection> getAllProductsByCategory(Long id) {
        return productRepository.findAllProduct(id);
    }

    @Override
    public void removeProduct(String token) {
        try {
            productRepository.removeByToken(token);
        } catch (Exception e) {
           e.printStackTrace();
            throw new ApiException(e.getMessage());
        }


    }
}
