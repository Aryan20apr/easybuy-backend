package com.example.easypay.config.security.authProviders;

import com.example.easypay.config.security.services.CustomerDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
@Slf4j
public class CustomerAuthProvider implements AuthenticationProvider {

    private final PasswordEncoder bcryptPasswordEncoder;
    private final CustomerDetailService customerDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        log.info("Username and password: "+username+" "+password);
        UserDetails customerDetails = customerDetailService.loadUserByUsername(username);
        if(customerDetails!=null){
            if(bcryptPasswordEncoder.matches(password,customerDetails.getPassword())){
                if(customerDetails.isEnabled()){
                    return new UsernamePasswordAuthenticationToken(username,password,customerDetails.getAuthorities());
                }
                else{
                    throw new DisabledException("Account has not been verified");
                }
            }
        }
        throw new BadCredentialsException("Wrong Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
