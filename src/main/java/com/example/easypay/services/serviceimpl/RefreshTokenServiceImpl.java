package com.example.easypay.services.serviceimpl;

import com.example.easypay.modals.entities.admins.Admin;
import com.example.easypay.modals.entities.customer.Customer;
import com.example.easypay.modals.entities.seller.Seller;
import com.example.easypay.modals.securitymodals.AdminRefreshToken;
import com.example.easypay.modals.securitymodals.CustomerRefreshToken;
import com.example.easypay.modals.securitymodals.RefreshToken;
import com.example.easypay.modals.securitymodals.SellerRefreshToken;
import com.example.easypay.repository.admin.AdminRepository;
import com.example.easypay.repository.customer.CustomerRepository;
import com.example.easypay.repository.RefreshTokenRepository;
import com.example.easypay.repository.seller.SellerRepository;
import com.example.easypay.services.interfaces.RefreshTokenService;
import com.example.easypay.utils.AppConstants;
import com.example.easypay.utils.exceptionUtil.ApiException;
import com.example.easypay.utils.exceptionUtil.RefreshTokenExpiredException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {


    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final AdminRepository adminRepository;
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(String username, String entityType) {
        if (entityType.equals(AppConstants.ENTITY_TYPE_SELLER)) {
            return getSellerRefreshToken(username);
        }
        else if (entityType.equals(AppConstants.ENTITY_TYPE_ADMIN)) {
            return getAdminRefreshToken(username);
        }
        else if (entityType.equals(AppConstants.ENTITY_TYPE_CUSTOMER)) {
            return getCustomerRefreshToken(username);
        }
        throw new ApiException("Invalid entity type: " + entityType);
    }

    @Override
    public Boolean verifyRefreshToken(String refreshToken) {
        RefreshToken token=this.refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new RuntimeException("No such token found"));
        if(token.getExpiresAt().isBefore(Instant.now()))
        {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException("Refresh Token is expired");
        }
        return true;

    }

    private RefreshToken getCustomerRefreshToken(String username) {
        Customer customer = this.customerRepository.findByEmail(username).orElseThrow(() -> new ApiException(("User with email: " + username + " does not exist")));
        Optional<RefreshToken> refreshTokenOptional = this.refreshTokenRepository.findByTypeAndId(AppConstants.ENTITY_TYPE_CUSTOMER, customer.getCustomerId());
        if (refreshTokenOptional.isPresent()) {
            RefreshToken refreshToken = refreshTokenOptional.get();
            refreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            CustomerRefreshToken customerRefreshToken = new CustomerRefreshToken();
            customerRefreshToken.setCustomer(this.customerRepository.findByEmail(username).orElseThrow(() -> new ApiException("User with username: " + username + " does not exist")));
            customerRefreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            customerRefreshToken.setRefreshToken(UUID.randomUUID().toString());
            customerRefreshToken = refreshTokenRepository.save(customerRefreshToken);
            return customerRefreshToken;
        }

    }

    private RefreshToken getSellerRefreshToken(String username) {
        Seller seller = this.sellerRepository.findByEmail(username).orElseThrow(() -> new ApiException(("User with email: " + username + " does not exist")));
        Optional<RefreshToken> refreshTokenOptional = this.refreshTokenRepository.findByTypeAndId(AppConstants.ENTITY_TYPE_SELLER, seller.getSellerId());
        if (refreshTokenOptional.isPresent()) {
            RefreshToken refreshToken = refreshTokenOptional.get();
            refreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            SellerRefreshToken sellerRefreshToken = new SellerRefreshToken();
            sellerRefreshToken.setSeller(this.sellerRepository.findByEmail(username).orElseThrow(() -> new ApiException("User with username: " + username + " does not exist")));
            sellerRefreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            sellerRefreshToken.setRefreshToken(UUID.randomUUID().toString());
            sellerRefreshToken = refreshTokenRepository.save(sellerRefreshToken);
            return sellerRefreshToken;
        }

    }
    private RefreshToken getAdminRefreshToken(String username) {
        Admin admin = this.adminRepository.findByEmail(username).orElseThrow(() -> new ApiException(("User with email: " + username + " does not exist")));
        Optional<RefreshToken> refreshTokenOptional = this.refreshTokenRepository.findByTypeAndId(AppConstants.ENTITY_TYPE_ADMIN, admin.getId());
        if (refreshTokenOptional.isPresent()) {
            RefreshToken refreshToken = refreshTokenOptional.get();
            refreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            AdminRefreshToken adminRefreshToken = new AdminRefreshToken();
            adminRefreshToken.setAdmin(this.adminRepository.findByEmail(username).orElseThrow(() -> new ApiException("User with username: " + username + " does not exist")));
            adminRefreshToken.setExpiresAt(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            adminRefreshToken.setRefreshToken(UUID.randomUUID().toString());
            adminRefreshToken = refreshTokenRepository.save(adminRefreshToken);
            return adminRefreshToken;
        }

    }

}


