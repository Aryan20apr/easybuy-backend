package com.example.easypay.services.interfaces;

import com.example.easypay.modals.dtos.cutomerdtos.CustomerDto;
import com.example.easypay.modals.projections.CustomerDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;
import jakarta.servlet.http.HttpServletResponse;

public interface CustomerService {

    public Boolean isCustomerPresent(String email);
    public String register(CustomerDto customerDto);

    public String login(HttpServletResponse httpServletResponse);

    public String getConsumerToken(String email);

    public CustomerDetailsProjection getCustomerDetails(String token);

    public void sendVerificationEmail(String email);

    public void verifyEmail(String email, Integer otp);

    public void sendOtp(Long phone);

    public void verifyOtp(Long phone, Integer otp);

    String sendPasswordResetOTP (String email, Long phone);

    String changePassword(ResetPasswordDto passwordChangeDto);

    String changePassword(NewPasswordDto passwordDto);
}
