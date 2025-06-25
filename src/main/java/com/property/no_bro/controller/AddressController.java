package com.property.no_bro.controller;

import com.property.no_bro.dto.ApiResponse;
import com.property.no_bro.dto.request.AddressRequest;
import com.property.no_bro.dto.response.AddressResponse;
import com.property.no_bro.exception.ResourceNotFoundException;
import com.property.no_bro.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest request) {
        AddressResponse response = addressService.createAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long addressId) {
        AddressResponse response = addressService.getAddressById(addressId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(@PathVariable Long addressId, @Valid @RequestBody AddressRequest request) {
        try {
            AddressResponse response = addressService.updateAddress(addressId, request);
            return ResponseEntity.ok(ApiResponse.success(response, "Address updated successfully", 200));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.failure(e.getMessage(), 404));
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByPropertyId(@PathVariable String propertyId) {
        List<AddressResponse> responses = addressService.getAddressesByPropertyId(propertyId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<AddressResponse>> getAddressesByCity(@PathVariable String city) {
        List<AddressResponse> responses = addressService.getAddressesByCity(city);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/city/{city}/state/{state}")
    public ResponseEntity<List<AddressResponse>> findByCityAndState(@PathVariable String city, @PathVariable String state) {
        List<AddressResponse> responses = addressService.findByCityAndState(city, state);
        return ResponseEntity.ok(responses);
    }
}