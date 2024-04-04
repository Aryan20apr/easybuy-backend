package com.example.easypay.services.serviceimpl;

import com.example.easypay.modals.entities.admins.AdminRole;
import com.example.easypay.repository.admin.AdminRolesRepository;
import com.example.easypay.services.interfaces.AdminRoleService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AdminRoleServiceImpl implements AdminRoleService {

    private final AdminRolesRepository adminRoleRespository;
    @Override
    public AdminRole getAdminRoleByRoleName(String roleName) {
        Optional<AdminRole> userRole=adminRoleRespository.findByRoleName(roleName);
        if(userRole.isPresent()){
            return userRole.get();
        }
        else{
            log.info(adminRoleRespository.findAll().toString());
            throw new ApiException("Consumer role with name: "+roleName+" does not exist");
        }
    }
}
