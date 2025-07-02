package com.property.no_bro.service;

import com.property.no_bro.dto.request.AddressRequest;
import com.property.no_bro.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse createAddress(AddressRequest addressRequest);
    AddressResponse getAddressById(Long addressId);
    AddressResponse updateAddress(Long addressId, AddressRequest addressRequest);
    void deleteAddress(Long addressId);
    List<AddressResponse> getAddressesByPropertyId(String propertyId); // Changed from Long to String

    List<AddressResponse> getAddressesByCity(String city);

    List<AddressResponse> findByCityAndState(String city, String state);
}