package com.example.easypay.modals.projections;

import java.util.Set;

public interface CustomerDetailsProjection {

    String getCustomerToken();
    String getName();
    String getEmail();
    String getMobile();
    String getGender();

//    Set<String> getRoles();

    String getVerificationStatus();


}