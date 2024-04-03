package com.example.easypay.controllers;


import com.example.easypay.modals.dtos.shared.ApiResponse;
import com.example.easypay.services.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UploadController {

    private UploadService uploadService;

    UploadController(UploadService uploadService)
    {
        this.uploadService=uploadService;
    }

    @PostMapping("/images")
    public ResponseEntity<ApiResponse<ArrayList<String>>> uploadImages( @RequestPart("images") MultipartFile[] images) {

        ArrayList<String> urls= uploadService.uploadImages(images);
        log.info("File received: "+images.length);
        log.info("File upload url: "+urls);
        ApiResponse<ArrayList<String>> apiResponse=ApiResponse.<ArrayList<String>>builder().data(urls).message("All images uploaded successfully").build();

        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @GetMapping("/demo")
    public ResponseEntity<String> getMessage()
    {
        return new ResponseEntity<>("Successfull",HttpStatus.OK);
    }
}
