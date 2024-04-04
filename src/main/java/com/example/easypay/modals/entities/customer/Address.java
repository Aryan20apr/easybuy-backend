package com.example.easypay.modals.entities.customer;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "house_number",nullable = false)
    private int houseNumber;

    @Column(nullable = false)
    private String addressLine1;

    private String addressLine2;

    @Column(nullable = false)
    private String city;

    private String state;

    @Column(nullable = false)
    private Long pincode;

    @ManyToMany(mappedBy = "addresses",fetch = FetchType.LAZY)
    private Set<Customer> customers=new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return getHouseNumber() == address.getHouseNumber() && Objects.equals(getId(), address.getId()) && Objects.equals(getAddressLine1(), address.getAddressLine1()) && Objects.equals(getAddressLine2(), address.getAddressLine2()) && Objects.equals(getCity(), address.getCity()) && Objects.equals(getState(), address.getState()) && Objects.equals(getPincode(), address.getPincode()) && Objects.equals(getCustomers(), address.getCustomers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHouseNumber(), getAddressLine1(), getAddressLine2(), getCity(), getState(), getPincode(), getCustomers());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", houseNumber=" + houseNumber +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pincode=" + pincode +
                ", customers=" + customers +
                '}';
    }

    public void addCustomer(Customer customer)
    {
        this.customers.add(customer);
        customer.getAddresses().add(this);
    }

    public void removeCustomer(Customer customer)
    {
        this.customers.remove(customer);
        customer.getAddresses().remove(this);
    }

}
