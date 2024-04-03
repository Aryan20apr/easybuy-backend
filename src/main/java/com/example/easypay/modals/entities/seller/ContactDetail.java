package com.example.easypay.modals.entities.seller;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "seller_contacts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long contact_id;

    String streetAddress;

    String city;

    String district;

    int zipCcde;

    String state;

    String customerCare;

    String email;

    @ManyToOne( cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")
    Seller seller;

    public void addSeller(Seller seller)
    {
        this.seller=seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactDetail that)) return false;
        return getZipCcde() == that.getZipCcde() && Objects.equals(getContact_id(), that.getContact_id()) && Objects.equals(getStreetAddress(), that.getStreetAddress()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getDistrict(), that.getDistrict()) && Objects.equals(getState(), that.getState()) && Objects.equals(getCustomerCare(), that.getCustomerCare()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContact_id(), getStreetAddress(), getCity(), getDistrict(), getZipCcde(), getState(), getCustomerCare(), getEmail());
    }
}
