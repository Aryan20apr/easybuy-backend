package com.example.easypay.services.interfaces;

import com.example.easypay.modals.entities.customer.CustomerRole;
import com.example.easypay.modals.entities.seller.SellerRole;

public interface SellerRoleService {
    SellerRole getSellerRoleByRoleName(String roleName);

}