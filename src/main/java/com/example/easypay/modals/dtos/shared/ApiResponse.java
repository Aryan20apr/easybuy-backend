package com.example.easypay.modals.dtos.shared;

import com.example.easypay.utils.AppConstants;
import lombok.*;

import java.util.ArrayList;

@Builder
@Getter
@Setter

public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(T data,String message) {
        this.data = data;
        this.code = AppConstants.SUCCESS_CODE;
        this.message = message;
    }
    public ApiResponse(T data) {
        this.data = data;
        this.code = AppConstants.SUCCESS_CODE;
        this.message = AppConstants.SUCCESS_MESSAGE;
    }

    public ApiResponse() {

    }
}
