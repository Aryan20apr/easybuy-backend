package com.example.easypay.config.security;

import com.example.easypay.config.security.services.Admin.AdminDetailService;
import com.example.easypay.config.security.services.customer.CustomerDetailService;
import com.example.easypay.config.security.services.seller.SellerDetailService;
import com.example.easypay.config.security.utils.JwtUtils;
import com.example.easypay.modals.dtos.shared.LoginRequestDto;
import com.example.easypay.utils.AppConstants;
import com.example.easypay.utils.exceptionUtil.ApiException;
import com.example.easypay.utils.exceptionUtil.InsufficientRolesException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.WebUtils;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import java.util.Arrays;

import static com.example.easypay.utils.AppConstants.AUTH_HEADER;
import static com.example.easypay.utils.AppConstants.PUBLIC_URLS;

@Component
@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Autowired
    private CustomerDetailService customerDetailsService;

    @Autowired
    private SellerDetailService sellerDetailsService;

    @Autowired
    private AdminDetailService adminDetailsService;

    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI(); //-get the uri
        log.info("Request:"+request);
        log.info("Request URI: "+uri);
        //-if the uri is a login uri, then login
        if (uri.endsWith(AppConstants.SIGN_IN_URI_ENDING)) {
            //-obtain username and password
            LoginRequestDto jwtAuthRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            String username = jwtAuthRequest.getUsername();
            String password = jwtAuthRequest.getPassword();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticationResult = null;
            try {
                authenticationResult = this.authenticationManager.authenticate(authenticationToken);

            } catch (AuthenticationException e) {

                SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.unauthenticated(username, password));
                exceptionResolver.resolveException(request, response, null, e);
            }
            if (authenticationResult != null) {
                SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            }
            log.info("URI ending with Login: "+uri);
            filterChain.doFilter(request, response);
        }
        //-if not a login uri, check for access token
        else {
            String headerToken = this.getTokenFromCookie(request); //-obtain token from cookie
            if (headerToken == null) {
                headerToken = request.getHeader(AUTH_HEADER); //-if no token, obtain token from header
            }
            //-if still not found, return
            if (headerToken == null) {
                System.out.println("Token is not present");
                //-match uri with public urls
               try{
                    boolean isPublicUrl = Arrays.stream(PUBLIC_URLS).anyMatch(uri::endsWith);
                    log.info("URI matched with Public Url: "+isPublicUrl);
                    if(isPublicUrl) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                    else{
                        throw new ApiException("Access token is not present");
//
                    }
                }
                catch (ApiException e){
                    exceptionResolver.resolveException(request, response, null, e);
                    return;
                }
            }
            UserDetails userDetails = null;
            try {
                headerToken = StringUtils.delete(headerToken, AppConstants.BEARER_TOKEN_PREFIX).trim();
                String entityType = JwtUtils.extractEntityType(headerToken);
                String username = JwtUtils.extractUsername(headerToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (entityType.equals(AppConstants.ENTITY_TYPE_CUSTOMER)) {
                        userDetails = this.customerDetailsService.loadUserByUsername(username);
                   }
                    else if (entityType.equals(AppConstants.ENTITY_TYPE_SELLER)) {
                        userDetails = this.sellerDetailsService.loadUserByUsername(username);
                    }
                    else if (entityType.equals(AppConstants.ENTITY_TYPE_ADMIN)) {
                        userDetails = this.adminDetailsService.loadUserByUsername(username);
                    }
 //                   else if (entityType.equals(AppConstants.ENTITY_TYPE_DEPARTMENT)) {
//                        userDetails = this.departmentDetailsService.loadUserByUsername(username);
//                    }
                    if (userDetails == null) {
                        throw new ApiException("User not found with username: " + username);
                    } else if (JwtUtils.validateToken(headerToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        filterChain.doFilter(request, response);
                    } else {
                        throw new ApiException("Token validation returned false");
                    }
                } else {
                    throw new ApiException("Username not found in token");
                }
            } catch (ExpiredJwtException | AccessDeniedException | UnsupportedJwtException | MalformedJwtException |
                     SignatureException | IllegalArgumentException | ApiException | InsufficientRolesException e) {
                SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.unauthenticated(userDetails, null));
                exceptionResolver.resolveException(request, response, null, e);
            }
        }
    }

    private String getTokenFromCookie(HttpServletRequest httpServletRequest) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, accessTokenCookieName);
        return cookie != null ? cookie.getValue() : null;
    }
}
