package com.example.easypay.services.interfaces;

import com.example.easypay.modals.dtos.cutomerdtos.CustomerDto;
import com.example.easypay.modals.dtos.projections.CustomerDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;

public interface CustomerService {

    public String register(CustomerDto customerDto);

    public String login(CustomerDto customerDto);

    public CustomerDetailsProjection getCustomerDetails();

    public void sendVerificationEmail();

    public void verifyEmail();

    public void sendOtp();

    public void verifyOtp();

    String sendPasswordResetOTP (String email, Long phone);

    String changePassword(ResetPasswordDto passwordChangeDto);

    String changePassword(NewPasswordDto passwordDto);
}
