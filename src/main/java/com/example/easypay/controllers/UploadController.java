package com.example.easypay.controllers;


import com.example.easypay.modals.dtos.ApiResponse;
import com.example.easypay.services.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UploadController {

    private UploadService uploadService;

    UploadController(UploadService uploadService)
    {
        this.uploadService=uploadService;
    }

    @PostMapping("/images")
    public ResponseEntity<ApiResponse> uploadImages( @RequestPart("images") MultipartFile[] images) {

        ArrayList<String> urls= uploadService.uploadImages(images);
        log.info("File received: "+images.length);
        log.info("File upload url: "+urls);
        ApiResponse apiResponse=ApiResponse.builder().url(urls).status(true).build();

        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @GetMapping("/demo")
    public ResponseEntity<String> getMessage()
    {
        return new ResponseEntity<>("Successfull",HttpStatus.OK);
    }
}
