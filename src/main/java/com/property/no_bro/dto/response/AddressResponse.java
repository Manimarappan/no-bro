package com.property.no_bro.dto.response;

import com.property.no_bro.model.Address;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressResponse {
    private Long addressId;
//    private String propertyId;
    private String street;
    private String landmark;
    private String location;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public AddressResponse(Address address) {
        this.addressId = address.getAddressId();
//        this.propertyId = address.getProperty() != null ? address.getProperty().getPropertyId() : null;
        this.street = address.getStreet();
        this.landmark = address.getLandmark();
        this.location = address.getLocation();
        this.city = address.getCity();
        this.state = address.getState();
        this.country = address.getCountry();
        this.pinCode = address.getPinCode();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
    }
}