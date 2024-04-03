package com.example.easypay.modals.dtos.sellerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ContactDetailDto {
    String streetAddress;

    String city;

    String district;

    int zipCcde;

    String state;

    String customerCare;

    String email;

}
