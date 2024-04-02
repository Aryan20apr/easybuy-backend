package com.example.easypay.services.serviceimpl;

import com.example.easypay.modals.entities.customer.CustomerRole;
import com.example.easypay.repository.customer.CustomerRoleRespository;
import com.example.easypay.services.interfaces.CustomerRoleService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerRoleServiceImpl implements CustomerRoleService {

    private final CustomerRoleRespository customerRoleRespository;
    @Override
    public CustomerRole getCustomerRoleByRoleName(String roleName) {
        Optional<CustomerRole> userRole=customerRoleRespository.findByRoleName(roleName);
        if(userRole.isPresent()){
            return userRole.get();
        }
        else{
            log.info(customerRoleRespository.findAll().toString());
            throw new ApiException("Consumer role with name: "+roleName+" does not exist");
        }
    }


}
