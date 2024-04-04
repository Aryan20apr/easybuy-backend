package com.example.easypay.services.interfaces;

import com.example.easypay.modals.entities.admins.AdminRole;

public interface AdminRoleService {

    AdminRole getAdminRoleByRoleName(String roleName);
}
