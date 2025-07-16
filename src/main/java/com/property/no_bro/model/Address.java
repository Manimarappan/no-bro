package com.property.no_bro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

//    @OneToOne
//    @JoinColumn(name = "propertyId", referencedColumnName = "propertyId")
//    private Property property;

    private String street;
    private String landmark;
    private String location;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
