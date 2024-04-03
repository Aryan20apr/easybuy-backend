package com.example.easypay.services.serviceimpl;

import com.example.easypay.modals.entities.customer.CustomerRole;
import com.example.easypay.modals.entities.seller.SellerRole;
import com.example.easypay.repository.customer.CustomerRoleRespository;
import com.example.easypay.repository.seller.SellerRoleRepository;
import com.example.easypay.services.interfaces.SellerRoleService;
import com.example.easypay.services.interfaces.SellerService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class SellerRoleServiceImpl implements SellerRoleService {
    private final SellerRoleRepository sellerRoleRespository;
    @Override
    public SellerRole getSellerRoleByRoleName(String roleName) {
        Optional<SellerRole> userRole=sellerRoleRespository.findByRoleName(roleName);
        if(userRole.isPresent()){
            return userRole.get();
        }
        else{
            log.info(sellerRoleRespository.findAll().toString());
            throw new ApiException("Seller role with name: "+roleName+" does not exist");
        }
    }
}
