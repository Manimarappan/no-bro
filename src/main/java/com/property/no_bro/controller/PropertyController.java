package com.property.no_bro.controller;

import com.property.no_bro.dto.request.PropertyRequest;
import com.property.no_bro.dto.response.AddressResponse;
import com.property.no_bro.dto.response.ImageResponse;
import com.property.no_bro.dto.response.PropertyResponse;
import com.property.no_bro.dto.ApiResponse;
import com.property.no_bro.exception.InvalidInputException;
import com.property.no_bro.exception.ResourceNotFoundException;
import com.property.no_bro.service.AddressService;
import com.property.no_bro.service.ImageService;
import com.property.no_bro.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ImageService imageService;

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
    @Transactional
    public ResponseEntity<ApiResponse<Void>> deleteProperty(@PathVariable String propertyId) {
        try {
            propertyService.deleteProperty(propertyId);
            return ResponseEntity.ok(ApiResponse.success(null, "Property deleted successfully", 200));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(ApiResponse.failure(e.getMessage(), 404));
        }
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getAllProperties() {
//        List<PropertyResponse> properties = propertyService.getAllProperties();
//        return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved successfully", 200));
//    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PropertyResponse>>> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PropertyResponse> properties = propertyService.getAllProperties(pageable);
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

    @GetMapping("/address/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getPropertyAddress(@PathVariable long id) {
        try{
            AddressResponse address = addressService.getAddressById(id);
            return ResponseEntity.ok(ApiResponse.success(address, "Properties retrieved by listed by", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<ApiResponse<ImageResponse>> getPropertyImage(@PathVariable Long id) {
        try {
            ImageResponse image = imageService.getImage(id);
            return ResponseEntity.ok(ApiResponse.success(image, "Image retrieved successfully", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }

    @GetMapping("/bhk/{bhk}")
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getPropertiesByBhk(@PathVariable String bhk) {
        try {
            List<PropertyResponse> properties = propertyService.getPropertiesByBhk(bhk);
            return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved by bhk", 200));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }

    // New method for filtered properties
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PropertyResponse>>> searchProperties(
            @RequestParam(defaultValue = "") String propertyType,
            @RequestParam(defaultValue = "") String bhk,
            @RequestParam(defaultValue = "") String furnishing,
            @RequestParam(defaultValue = "0") double rent,
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) { // Matches frontend pageSize=5
        Pageable pageable = PageRequest.of(page, size);
        Page<PropertyResponse> properties = propertyService.searchProperties(
                propertyType, bhk, furnishing, rent, city, pageable);
        return ResponseEntity.ok(ApiResponse.success(properties, "Filtered properties retrieved successfully", 200));
    }

    @GetMapping("/userid/{userId}")
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getProperty(@PathVariable long userId) {
        try{
            List<PropertyResponse> properties = propertyService.getUserId(userId);
            return ResponseEntity.ok(ApiResponse.success(properties, "Properties retrieved by furnishing", 200));
        }catch (InvalidInputException e) {
            return ResponseEntity.status(400).body(ApiResponse.failure(e.getMessage(), 400));
        }
    }


}