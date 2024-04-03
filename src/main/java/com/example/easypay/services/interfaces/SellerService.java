package com.example.easypay.services.interfaces;

import com.example.easypay.modals.dtos.sellerDto.SellerDto;

import com.example.easypay.modals.dtos.projections.SellerDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;
import jakarta.servlet.http.HttpServletResponse;

public interface SellerService {

    public Boolean isSellerPresent(String email);
    public String register(SellerDto SellerDto);

    public String login(HttpServletResponse httpServletResponse);

    public String getSellerToken(String email);

    public SellerDetailsProjection getSellerDetails();

    public void sendVerificationEmail();

    public void verifyEmail();

    public void sendOtp();

    public void verifyOtp();

    String sendPasswordResetOTP (String email, Long phone);

    String changePassword(ResetPasswordDto passwordChangeDto);

    String changePassword(NewPasswordDto passwordDto);
}
