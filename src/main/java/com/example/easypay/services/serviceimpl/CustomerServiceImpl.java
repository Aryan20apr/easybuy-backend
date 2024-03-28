package com.example.easypay.services.serviceimpl;

import com.example.easypay.modals.dtos.CustomerDto;
import com.example.easypay.modals.dtos.projections.CustomerDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;
import com.example.easypay.services.interfaces.CustomerService;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public String register(CustomerDto customerDto) {
        return null;
    }

    @Override
    public String login(CustomerDto customerDto) {
        return null;
    }

    @Override
    public CustomerDetailsProjection getCustomerDetails() {
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
