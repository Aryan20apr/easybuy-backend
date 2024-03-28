package com.example.easypay.modals.dtos;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {


    private Long id;


    private int houseNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private Long pincode;
}
