package com.example.easypay.controllers;

import com.example.easypay.modals.dtos.cutomerdtos.CustomerDto;
import com.example.easypay.modals.dtos.shared.ApiResponse;
import com.example.easypay.services.interfaces.CustomerService;
import com.example.easypay.services.serviceimpl.CustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private CustomerService customerService;
    CustomerController(CustomerService customerService)
    {
        this.customerService=customerService;
    }

@PostMapping("/register")
   public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody CustomerDto customerDto)
    {
        return new ResponseEntity<>(new ApiResponse<String>(), HttpStatus.CREATED);
    }
   public ResponseEntity<ApiResponse> loginUser(@RequestBody CustomerDto customerDto)
    {
        return new ResponseEntity<>(new ApiResponse(), HttpStatus.OK);
    }



}


