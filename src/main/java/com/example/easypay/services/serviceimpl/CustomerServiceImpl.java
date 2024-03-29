package com.example.easypay.services.serviceimpl;

import com.example.easypay.modals.dtos.cutomerdtos.CustomerDto;
import com.example.easypay.modals.dtos.projections.CustomerDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;
import com.example.easypay.repository.CustomerRepository;
import com.example.easypay.services.interfaces.CustomerRoleService;
import com.example.easypay.services.interfaces.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository consumerRepository;
    private final CustomerRoleService consumerRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
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
