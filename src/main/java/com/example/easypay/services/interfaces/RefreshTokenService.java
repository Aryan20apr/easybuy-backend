package com.example.easypay.services.interfaces;

import com.example.easypay.modals.securitymodals.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username, String entityType);

    Boolean verifyRefreshToken(String refreshToken);
}
