package com.example.easypay.utils.exceptionUtil;
/*
 * ApiException- class representing custom exceptions thrown in the business logic, for sending a response to the client
 */
public class ApiException extends RuntimeException{
    
    public ApiException(String message){
        super(message);
    }
}
