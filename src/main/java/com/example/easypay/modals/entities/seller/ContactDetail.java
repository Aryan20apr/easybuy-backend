package com.example.easypay.modals.entities.seller;

import jakarta.persistence.*;

@Entity
@Table(name = "seller_contacts")
public class ContactDetail {

    @Id
    Long contact_id;

    String streetAddress;

    String city;

    String district;

    int zipCcde;

    String state;

    String customerCare;

    String email;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")
    Seller seller;


}
