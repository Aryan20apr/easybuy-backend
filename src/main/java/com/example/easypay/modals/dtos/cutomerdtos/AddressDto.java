package com.example.easypay.modals.dtos.cutomerdtos;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {




    private int houseNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private Long pincode;
}
