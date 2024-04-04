package com.example.easypay.services.interfaces;

import com.example.easypay.modals.dtos.admindtos.AdminDto;

import com.example.easypay.modals.dtos.projections.AdminDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AdminService {

    public Boolean isAdminPresent(String email);
    public String register(AdminDto AdminDto);

    public String login(HttpServletResponse httpServletResponse);

    public String getAdminToken(String email);

    public AdminDetailsProjection getAdminDetails(String token);

    public void sendVerificationEmail();

    public void verifyEmail();

    public void sendOtp();

    public void verifyOtp();

    String sendPasswordResetOTP (String email, Long phone);

    String changePassword(ResetPasswordDto passwordChangeDto);

    String changePassword(NewPasswordDto passwordDto);
}
