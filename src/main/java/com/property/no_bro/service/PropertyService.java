package com.property.no_bro.service;

import com.property.no_bro.dto.request.PropertyRequest;
import com.property.no_bro.dto.response.PropertyResponse;
import com.property.no_bro.model.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyService {
    PropertyResponse saveProperty(PropertyRequest propertyRequest);
    Optional<PropertyResponse> getPropertyById(String propertyId);
    PropertyResponse updateProperty(String propertyId, PropertyRequest propertyDetails);
    void deleteProperty(String propertyId);
    List<PropertyResponse> getAllProperties();
    List<PropertyResponse> getPropertiesByType(String propertyType);
    List<PropertyResponse> getPropertiesByFurnishing(String furnishing);
//    List<PropertyResponse> getAvailableProperties(String status);
    List<PropertyResponse> getPropertiesByPriceRange(double minPrice, double maxPrice);
    List<PropertyResponse> getPropertiesByBedrooms(int bedrooms);
    List<PropertyResponse> getPropertiesByStatus(String status);
    List<PropertyResponse> getPropertiesByListedBy(String listedBy);

}