package com.example.easypay.services.serviceimpl;

import com.example.easypay.config.security.utils.JwtUtils;
import com.example.easypay.modals.dtos.cutomerdtos.AddressDto;
import com.example.easypay.modals.dtos.cutomerdtos.CustomerDto;
import com.example.easypay.modals.dtos.projections.CustomerDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;
import com.example.easypay.modals.entities.Address;
import com.example.easypay.modals.entities.Customer;
import com.example.easypay.modals.entities.CustomerRole;
import com.example.easypay.modals.enums.Verification;
import com.example.easypay.repository.CustomerRepository;
import com.example.easypay.repository.CustomerRoleRespository;
import com.example.easypay.services.interfaces.CustomerRoleService;
import com.example.easypay.services.interfaces.CustomerService;
import com.example.easypay.utils.AppConstants;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRoleRespository customerRoleRespository;

    private final CustomerRepository customerRepository;
    private final CustomerRoleService customerRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public Boolean isCustomerPresent(String email)
    {
        return customerRepository.existsByEmail(email);
    }
    @Override
    public String register(CustomerDto customerDto) {

        String email=customerDto.getEmail();
        Boolean isCustomerPresent=isCustomerPresent(email);

        if(isCustomerPresent)
        {
            throw new ApiException("Customer with this email already exists");
        }
        else
        {
            Customer customer=new Customer();
            Set<CustomerRole> roles=new HashSet<>();
            Set<String> customerRoles=customerDto.getRoles();
          
            customerRoles.forEach(customerRole -> {
                CustomerRole role = customerRoleService.getCustomerRoleByRoleName(customerRole);
                roles.add(role);
            });
            customer.setEmail(email);
            customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
            customer.setRoles(roles);
            customer.setMobile(customerDto.getMobile());
            customer.setGender(customerDto.getGender());
            customer.setName(customer.getName());
            customer.setVerificationStatus(Verification.UNVERIFIED);
            if (customerDto.getAddress() != null) {
                Address customerAddress = this.modelMapper.map(customerDto.getAddress(), Address.class);
                customer.addAddress(customerAddress);
            }
            customerRepository.save(customer);
            log.info(customer.toString());
            return customer.getCustomerToken();
        }
    }
    @Override
    public String getConsumerToken(String email) {
        return customerRepository.findTokenByEmail(email);
    }

    @Override
    public String login(HttpServletResponse httpServletResponse) {
      return "";
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
