package com.example.easypay.services.serviceimpl;

import com.example.easypay.modals.dtos.admindtos.AdminDto;
import com.example.easypay.modals.dtos.projections.AdminDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;
import com.example.easypay.modals.entities.admins.Admin;
import com.example.easypay.modals.entities.admins.AdminRole;
import com.example.easypay.repository.admin.AdminRepository;
import com.example.easypay.repository.admin.AdminRolesRepository;
import com.example.easypay.services.interfaces.AdminRoleService;
import com.example.easypay.services.interfaces.AdminService;

import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService {


    private final AdminRolesRepository adminRoleRepository;

    private final AdminRepository adminRepository;
    private final AdminRoleService adminRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Override
    public Boolean isAdminPresent(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Override
    public String register(AdminDto adminDto) {
        String email=adminDto.getEmail();
        Boolean isAdminPresent=isAdminPresent(email);

        if(isAdminPresent)
        {
            throw new ApiException("Admin with this email already exists");
        }
        else
        {
           Admin admin=new Admin();
            Set<AdminRole> roles=new HashSet<>();
            Set<String> adminRoles=adminDto.getRoles();

            adminRoles.forEach(adminRole -> {
                AdminRole role = adminRoleService.getAdminRoleByRoleName(adminRole);
                roles.add(role);
            });
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
            admin.setRoles(roles);

            adminRepository.save(admin);
            log.info(admin.toString());
            return admin.getAdminToken();
        }
    }

    @Override
    public String login(HttpServletResponse httpServletResponse) {
        return null;
    }

    @Override
    public String getAdminToken(String email) {
        return adminRepository.findTokenByEmail(email);
    }

    @Override
    public AdminDetailsProjection getAdminDetails(String token) {
        return null;
    }

    @Override
    public void sendVerificationEmail() {

    }

    @Override
    public void verifyEmail() {

    }

    @Override
    public void sendOtp() {

    }

    @Override
    public void verifyOtp() {

    }

    @Override
    public String sendPasswordResetOTP(String email, Long phone) {
        return null;
    }

    @Override
    public String changePassword(ResetPasswordDto passwordChangeDto) {
        return null;
    }

    @Override
    public String changePassword(NewPasswordDto passwordDto) {
        return null;
    }
}
