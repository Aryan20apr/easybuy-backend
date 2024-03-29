package com.example.easypay.utils.exceptionUtil;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException(String s) {
        super(s);
    }
}
