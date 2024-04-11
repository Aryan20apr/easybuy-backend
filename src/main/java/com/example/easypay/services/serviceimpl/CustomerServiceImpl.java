package com.example.easypay.services.serviceimpl;

import com.example.easypay.config.email.EmailConfigurationProperties;
import com.example.easypay.config.twilio.TwilioConfigurationProperties;
import com.example.easypay.modals.dtos.cutomerdtos.CustomerDto;
import com.example.easypay.modals.entities.cart.Cart;
import com.example.easypay.modals.projections.CustomerDetailsProjection;
import com.example.easypay.modals.dtos.shared.NewPasswordDto;
import com.example.easypay.modals.dtos.shared.ResetPasswordDto;
import com.example.easypay.modals.entities.customer.Address;
import com.example.easypay.modals.entities.customer.Customer;
import com.example.easypay.modals.entities.customer.CustomerRole;
import com.example.easypay.modals.enums.UserVerification;
import com.example.easypay.repository.cart.CartRepository;
import com.example.easypay.repository.customer.CustomerRepository;
import com.example.easypay.repository.customer.CustomerRoleRespository;
import com.example.easypay.services.interfaces.CustomerRoleService;
import com.example.easypay.services.interfaces.CustomerService;
import com.example.easypay.utils.emailUtil.AccountVerificationEmailContext;
import com.example.easypay.utils.emailUtil.MailService;
import com.example.easypay.utils.exceptionUtil.ApiException;
import com.example.easypay.utils.otpUtil.OtpService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    private final MailService mailService;
    private final OtpService otpService;
    private final EmailConfigurationProperties emailConfigurationProperties;
    private final TwilioConfigurationProperties twilioConfigurationProperties;
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
            customer.setName(customerDto.getName());
            customer.setUserVerificationStatus(UserVerification.UNVERIFIED);
            if (customerDto.getAddress() != null) {
                Address customerAddress = this.modelMapper.map(customerDto.getAddress(), Address.class);
                customer.addAddress(customerAddress);
            }

            Cart cart= Cart.builder().build();
            cartRepository.save(cart);
//            customer.setCart(cart);

            //customerRepository.save(customer);
            //log.info(customer.toString());
            //log.info("Customer saved id:"+customer.getCustomerId());
            cart.setCustomer(customer);
            cartRepository.save(cart);
            customerRepository.save(customer);
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
    public CustomerDetailsProjection getCustomerDetails(String token) {


            CustomerDetailsProjection customerDetails= customerRepository.getCustomerDetails(token);
            log.info("customerDetails="+(customerDetails==null));
            return customerDetails;
        }


    @Override
    public void sendVerificationEmail(String email) {
        int otp = otpService.generateOTP(email);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext(emailConfigurationProperties);
        emailContext.init();
        emailContext.setTo(email);
        emailContext.setOTP(String.valueOf(otp));
        try {
            mailService.sendEmail(emailContext);
        } catch (MessagingException e) {
            log.error("Some error occurred while sending email: " + e.getMessage());
        }
    }

    @Override
    public void verifyEmail(String email, Integer otp) {
        int storedOTP = otpService.getOTP(email);
        log.info("Stored otp: " + storedOTP + " received otp: " + otp);
        if (storedOTP != otp) {
            throw new ApiException("The otp is either incorrect or expired!");
        }
        otpService.clearOTP(email);
    }

    @Override
    public void sendOtp(Long phone) {
        Twilio.init(twilioConfigurationProperties.accountSid(), twilioConfigurationProperties.authToken());
        Verification verification = Verification.creator(twilioConfigurationProperties.serviceSid(),
                        phone.toString(),
                        "sms")
                .create();
        log.info("otp sent to: {}", phone);
    }

    @Override
    public void verifyOtp(Long phone, Integer otp) {
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            twilioConfigurationProperties.serviceSid())
                    .setTo(phone.toString())
                    .setCode(otp.toString())
                    .create();
            log.info("verification status: {}", verificationCheck.getStatus());
        } catch (Exception e) {
            throw new ApiException("The otp is either incorrect or expired!");
        }
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
