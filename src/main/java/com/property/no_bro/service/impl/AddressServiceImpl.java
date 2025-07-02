package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.AddressRequest;
import com.property.no_bro.dto.response.AddressResponse;
import com.property.no_bro.exception.ResourceNotFoundException;
import com.property.no_bro.model.Address;
import com.property.no_bro.model.Property;
import com.property.no_bro.repository.AddressRepository;
import com.property.no_bro.repository.PropertyRepository;
import com.property.no_bro.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final PropertyRepository propertyRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, PropertyRepository propertyRepository) {
        this.addressRepository = addressRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public AddressResponse createAddress(AddressRequest request) {
        if (request.getPropertyId() == null) {
            throw new IllegalArgumentException("Property ID must not be null");
        }
        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + request.getPropertyId()));

        Address address = new Address();
//        address.setProperty(property);
        address.setStreet(request.getStreet());
        address.setLandmark(request.getLandmark());
        address.setArea(request.getArea());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPinCode(request.getPinCode());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());

        Address savedAddress = addressRepository.save(address);
        return new AddressResponse(savedAddress);
    }

    @Override
    public AddressResponse getAddressById(Long addressId) {
        if (addressId == null) {
            throw new IllegalArgumentException("Address ID must not be null");
        }
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
        return new AddressResponse(address);
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressRequest request) {
        if (addressId == null) {
            throw new IllegalArgumentException("Address ID must not be null");
        }
        if (request.getPropertyId() == null) {
            throw new IllegalArgumentException("Property ID must not be null");
        }
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + request.getPropertyId()));

        address.setProperty(property);
        address.setStreet(request.getStreet());
        address.setLandmark(request.getLandmark());
        address.setArea(request.getArea());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPinCode(request.getPinCode());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());

        Address savedAddress = addressRepository.save(address);
        return new AddressResponse(savedAddress);
    }

    @Override
    public void deleteAddress(Long addressId) {
        if (addressId == null) {
            throw new IllegalArgumentException("Address ID must not be null");
        }
        if (!addressRepository.existsById(addressId)) {
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
        addressRepository.deleteById(addressId);
    }

    @Override
    public List<AddressResponse> getAddressesByPropertyId(String propertyId) {
        if (propertyId == null) {
            throw new IllegalArgumentException("Property ID must not be null");
        }
        return addressRepository.findByPropertyPropertyId(propertyId)
                .stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddressResponse> getAddressesByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City must not be empty");
        }
        return addressRepository.findByCity(city)
                .stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddressResponse> findByCityAndState(String city, String state) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City must not be empty");
        }
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State must not be empty");
        }
        return addressRepository.findByCityAndState(city, state)
                .stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());
    }
}