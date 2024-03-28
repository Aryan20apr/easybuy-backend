package com.example.easypay.modals.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Getter
@Setter
public class ApiResponse {

    ArrayList<String> url;
    boolean status;
}
