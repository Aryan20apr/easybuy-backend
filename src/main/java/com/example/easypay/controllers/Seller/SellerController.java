package com.example.easypay.controllers.Seller;

import com.example.easypay.config.security.utils.JwtUtils;
import com.example.easypay.modals.dtos.cutomerdtos.CustomerDto;
import com.example.easypay.modals.dtos.shared.ApiResponse;
import com.example.easypay.modals.dtos.shared.LoginResponseDto;
import com.example.easypay.modals.dtos.shared.RefreshTokenDto;
import com.example.easypay.modals.securitymodals.RefreshToken;
import com.example.easypay.services.interfaces.CustomerService;
import com.example.easypay.services.interfaces.RefreshTokenService;
import com.example.easypay.utils.AppConstants;
import com.example.easypay.utils.CookieUtils;
import com.example.easypay.utils.exceptionUtil.ApiException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("easybuy/api/v1/customer")

public class SellerController {

    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Value("${jwt.refreshTokenCookieName}")
    private String refreshTokenCookieName;


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
            CookieUtils.create(httpServletResponse, refreshTokenCookieName, refreshToken.getRefreshToken(), false, -1, "localhost");
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
        CookieUtils.clear(response,accessTokenCookieName);
        CookieUtils.clear(response,refreshTokenCookieName);

        return new ResponseEntity<>(new ApiResponse<>("Logged out successfully"),HttpStatus.OK);
    }

    @PutMapping("/refresh-token")
    public ResponseEntity<ApiResponse<String>> refreshJwtToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse httpServletResponse) {
        Boolean isRefreshTokenValid=this.refreshTokenService.verifyRefreshToken(refreshTokenDto.getRefreshToken());
        if(isRefreshTokenValid){
            String token= JwtUtils.generateToken(refreshTokenDto.getUsername(), AppConstants.ENTITY_TYPE_CUSTOMER);
            CookieUtils.create(httpServletResponse, accessTokenCookieName, token, false, -1, "localhost");
            return new ResponseEntity<>(new ApiResponse<>(token),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ApiResponse<>("The refresh token is expired! Cannot generate a new token! Please re-login"),HttpStatus.OK);
        }
    }




}