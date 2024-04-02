package com.example.easypay.services.interfaces;

import com.example.easypay.modals.entities.customer.CustomerRole;

public interface CustomerRoleService {
    CustomerRole getCustomerRoleByRoleName(String roleName);

}
