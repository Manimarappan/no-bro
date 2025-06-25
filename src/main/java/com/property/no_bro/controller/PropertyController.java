package com.property.no_bro.controller;

import com.property.no_bro.dto.request.PropertyRequest;
import com.property.no_bro.dto.response.PropertyResponse;
import com.property.no_bro.dto.ApiResponse;
import com.property.no_bro.exception.InvalidInputException;
import com.property.no_bro.exception.ResourceNotFoundException;
import com.property.no_bro.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<ApiResponse<PropertyResponse>> createProperty(@Valid @RequestBody PropertyRequest propertyRequest) {
        PropertyResponse savedProperty = propertyService.saveProperty(propertyRequest);
        return ResponseEntity.status(201).body(ApiResponse.success(savedProperty, "Property created successfully", 201));
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<PropertyResponse>> getProperty(@PathVariable String propertyId) {
        return propertyService.getPropertyById(propertyId)
                .map(property -> ResponseEntity.ok(ApiResponse.success(property, "Property retrieved successfully", 200)))
                .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.failure("Property not found", 404)));
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<PropertyResponse>> updateProperty(@PathVariable String propertyId, @Valid @RequestBody PropertyRequest propertyDetails) {
        try {
            PropertyResponse updatedProperty = propertyService.updateProperty(propertyId, propertyDetails);
            return ResponseEntity.ok(ApiResponse.success(updatedProperty, "Property updated successfully", 200));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(ApiResponse.failure(e.getMessage(), 404));
        }
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<Void>> deleteProperty(@PathVariable String propertyId) {
        try {
            propertyService.deleteProperty(propertyId);
            return ResponseEntity.ok(ApiResponse.success(null, "Property deleted successfully", 200));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(ApiResponse.failure(e.getMessage(), 404));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getAllProperties() {
        List<PropertyResponse> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved successfully", 200));
    }

    @GetMapping("/type/{propertyType}")
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getPropertiesByType(@PathVariable String propertyType) {
        try {
            List<PropertyResponse> properties = propertyService.getPropertiesByType(propertyType);
            return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved by type", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }

    @GetMapping("/furnishing/{furnishing}")
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getPropertiesByFurnishing(@PathVariable String furnishing) {
        try {
            List<PropertyResponse> properties = propertyService.getPropertiesByFurnishing(furnishing);
            return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved by furnishing", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }

//    @GetMapping("/available")
//    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getAvailableProperties(@RequestParam String status) {
//        List<PropertyResponse> properties = propertyService.getAvailableProperties(status);
//        return ResponseEntity.ok(ApiResponse.success(properties, "Available properties retrieved", 200));
//    }

    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getPropertiesByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        try {
            List<PropertyResponse> properties = propertyService.getPropertiesByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved by price range", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }

    @GetMapping("/bedrooms/{bedrooms}")
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getPropertiesByBedrooms(@PathVariable int bedrooms) {
        try {
            List<PropertyResponse> properties = propertyService.getPropertiesByBedrooms(bedrooms);
            return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved by bedrooms", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getPropertiesByStatus(@PathVariable String status) {
        try {
            List<PropertyResponse> properties = propertyService.getPropertiesByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved by status", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }

    @GetMapping("/listed-by/{listedBy}")
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getPropertiesByListedBy(@PathVariable String listedBy) {
        try {
            List<PropertyResponse> properties = propertyService.getPropertiesByListedBy(listedBy);
            return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved by listed by", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }
}