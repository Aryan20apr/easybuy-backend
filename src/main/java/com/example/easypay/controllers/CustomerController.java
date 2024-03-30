package com.example.easypay.controllers;

import com.example.easypay.config.security.utils.JwtUtils;
import com.example.easypay.modals.dtos.cutomerdtos.CustomerDto;
import com.example.easypay.modals.dtos.shared.ApiResponse;
import com.example.easypay.modals.dtos.shared.LoginRequestDto;
import com.example.easypay.modals.dtos.shared.LoginResponseDto;
import com.example.easypay.modals.securitymodals.RefreshToken;
import com.example.easypay.services.interfaces.CustomerService;
import com.example.easypay.services.interfaces.RefreshTokenService;
import com.example.easypay.services.serviceimpl.CustomerServiceImpl;
import com.example.easypay.utils.AppConstants;
import com.example.easypay.utils.CookieUtils;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("easypay/api/v1/customer")

public class CustomerController {

    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    private RefreshTokenService refreshTokenService;

    private CustomerService customerService;
    CustomerController(CustomerService customerService, RefreshTokenService refreshTokenService)
    {
        this.customerService=customerService;
        this.refreshTokenService=refreshTokenService;
    }

@PostMapping("/register")
   public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody CustomerDto customerDto)
    {
        String customerToken=customerService.register(customerDto);
        return new ResponseEntity<>(new ApiResponse<String>(customerToken), HttpStatus.CREATED);
    }
    @PostMapping("/login")
   public ResponseEntity<ApiResponse> loginUser(HttpServletResponse httpServletResponse)
    {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
        {
            String username=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String token=customerService.getConsumerToken(username);
            String accessToken= JwtUtils.generateToken(username, AppConstants.ENTITY_TYPE_CUSTOMER);
            CookieUtils.create(httpServletResponse, accessTokenCookieName, accessToken, false, -1, "localhost");
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(username, AppConstants.ENTITY_TYPE_CUSTOMER);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setAccessToken(accessToken);
            loginResponseDto.setToken(token);
            loginResponseDto.setUsername(username);
            loginResponseDto.setRefreshToken(refreshToken.getRefreshToken());
            return new ResponseEntity<>(new ApiResponse<>(loginResponseDto),HttpStatus.OK);
        }
        else
        {
            throw new ApiException("Customer authentication failed");
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response){
        //CookieUtils.clear(response,accessTokenCookieName);
        return new ResponseEntity<>(new ApiResponse<>("Logged out successfully"),HttpStatus.OK);
    }



}


