package com.property.no_bro.service;

import com.property.no_bro.dto.ApiResponse;
import com.property.no_bro.dto.request.PropertyRequest;
import com.property.no_bro.dto.response.PropertyResponse;
import com.property.no_bro.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PropertyService {
    PropertyResponse saveProperty(PropertyRequest propertyRequest);
    Optional<PropertyResponse> getPropertyById(String propertyId);
    PropertyResponse updateProperty(String propertyId, PropertyRequest propertyDetails);
    void deleteProperty(String propertyId);
//    List<PropertyResponse> getAllProperties();
    Page<PropertyResponse> getAllProperties(Pageable pageable);
    List<PropertyResponse> getPropertiesByType(String propertyType);
    List<PropertyResponse> getPropertiesByFurnishing(String furnishing);
//    List<PropertyResponse> getAvailableProperties(String status);
    List<PropertyResponse> getPropertiesByPriceRange(double minPrice, double maxPrice);
    List<PropertyResponse> getPropertiesByBedrooms(int bedrooms);
    List<PropertyResponse> getPropertiesByStatus(String status);
    List<PropertyResponse> getPropertiesByListedBy(String listedBy);

    List<PropertyResponse> getPropertiesByBhk(String bhk);

    Page<PropertyResponse> searchProperties(String propertyType, String bhk, String furnishing, double rent, String city, Pageable pageable);

    List<PropertyResponse> getUserId(long userId);
}