package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.PropertyRequest;
import com.property.no_bro.dto.response.PropertyResponse;
import com.property.no_bro.model.Property;
import com.property.no_bro.model.User;
import com.property.no_bro.repository.PropertyRepository;
import com.property.no_bro.repository.UserRepository;
import com.property.no_bro.service.PropertyService;
import com.property.no_bro.enums.Furnishing;
import com.property.no_bro.enums.ListedBy;
import com.property.no_bro.enums.PropertyType;
import com.property.no_bro.exception.InvalidInputException;
import com.property.no_bro.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PropertyResponse saveProperty(PropertyRequest propertyRequest) {
        if (propertyRequest.getUserId() == null) {
            throw new InvalidInputException("User ID is required");
        }
        User user = userRepository.findById(propertyRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + propertyRequest.getUserId()));

        Property property = new Property();
        property.setPropertyName(propertyRequest.getPropertyName());
        property.setPropertyType(propertyRequest.getPropertyType());
        property.setFurnishing(propertyRequest.getFurnishing());
        property.setStatus(propertyRequest.getStatus());
        property.setPrice(propertyRequest.getPrice());
        property.setArea(propertyRequest.getArea());
        property.setBedrooms(propertyRequest.getBedrooms());
        property.setBathrooms(propertyRequest.getBathrooms());
        property.setBalconies(propertyRequest.getBalconies());
        property.setFloorNumber(propertyRequest.getFloorNumber());
        property.setTotalFloors(propertyRequest.getTotalFloors());
        property.setParking(propertyRequest.isParking());
        property.setYearBuilt(propertyRequest.getYearBuilt());
        property.setListedBy(propertyRequest.getListedBy());
        property.setUser(user);
        property.setFeatured(propertyRequest.isFeatured());
        property.setViewsCount(0); // Initialize views to 0
        property.setCreatedAt(LocalDateTime.now());
        property.setUpdatedAt(LocalDateTime.now());


        Property savedProperty = propertyRepository.save(property);
        return new PropertyResponse(savedProperty);
    }

    @Override
    public Optional<PropertyResponse> getPropertyById(String propertyId) {
        return propertyRepository.findById(propertyId).map(PropertyResponse::new);
    }

    @Override
    public PropertyResponse updateProperty(String propertyId, PropertyRequest propertyDetails) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId));
        if (propertyDetails.getPropertyName() != null) property.setPropertyName(propertyDetails.getPropertyName());
        if (propertyDetails.getPropertyType() != null) property.setPropertyType(propertyDetails.getPropertyType());
        if (propertyDetails.getFurnishing() != null) property.setFurnishing(propertyDetails.getFurnishing());
        if (propertyDetails.getStatus() != null) property.setStatus(propertyDetails.getStatus());
        if (propertyDetails.getPrice() > 0) property.setPrice(propertyDetails.getPrice());
        if (propertyDetails.getArea() > 0) property.setArea(propertyDetails.getArea());
        if (propertyDetails.getBedrooms() >= 0) property.setBedrooms(propertyDetails.getBedrooms());
        if (propertyDetails.getBathrooms() >= 0) property.setBathrooms(propertyDetails.getBathrooms());
        if (propertyDetails.getBalconies() >= 0) property.setBalconies(propertyDetails.getBalconies());
        if (propertyDetails.getFloorNumber() >= 0) property.setFloorNumber(propertyDetails.getFloorNumber());
        if (propertyDetails.getTotalFloors() >= 0) property.setTotalFloors(propertyDetails.getTotalFloors());
        if (propertyDetails.isParking()) property.setParking(propertyDetails.isParking());
        if (propertyDetails.getYearBuilt() >= 1900) property.setYearBuilt(propertyDetails.getYearBuilt());
        if (propertyDetails.getListedBy() != null) property.setListedBy(propertyDetails.getListedBy());
        property.setFeatured(propertyDetails.isFeatured());
        property.setUpdatedAt(LocalDateTime.now());

        return new PropertyResponse(propertyRepository.save(property));
    }

    @Override
    public void deleteProperty(String propertyId) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new ResourceNotFoundException("Property not found with id: " + propertyId);
        }
        propertyRepository.deleteById(propertyId);
    }

    @Override
    public List<PropertyResponse> getAllProperties() {
        return propertyRepository.findAll().stream().map(PropertyResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponse> getPropertiesByType(String propertyType) {
        try {
            PropertyType type = PropertyType.valueOf(propertyType.toUpperCase());
            return propertyRepository.findByPropertyType(type).stream()
                    .map(PropertyResponse::new).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid property type: " + propertyType);
        }
    }

    @Override
    public List<PropertyResponse> getPropertiesByFurnishing(String furnishing) {
        try {
            Furnishing furnish = Furnishing.valueOf(furnishing.toUpperCase());
            return propertyRepository.findByFurnishing(furnish).stream()
                    .map(PropertyResponse::new).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid furnishing type: " + furnishing);
        }
    }

//    @Override
//    public List<PropertyResponse> getAvailableProperties(String status) {
//        return propertyRepository.findByStatus(status).stream()
//                .map(PropertyResponse::new).collect(Collectors.toList());
//    }

    @Override
    public List<PropertyResponse> getPropertiesByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new InvalidInputException("Invalid price range");
        }
        return propertyRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(PropertyResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponse> getPropertiesByBedrooms(int bedrooms) {
        if (bedrooms < 0) {
            throw new InvalidInputException("Bedrooms cannot be negative");
        }
        return propertyRepository.findByBedroomsGreaterThanEqual(bedrooms).stream()
                .map(PropertyResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponse> getPropertiesByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new InvalidInputException("Status is required");
        }
        return propertyRepository.findByStatus(status).stream()
                .map(PropertyResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponse> getPropertiesByListedBy(String listedBy) {
        try {
            ListedBy listBy = ListedBy.valueOf(listedBy.toUpperCase());
            return propertyRepository.findByListedBy(listBy).stream()
                    .map(PropertyResponse::new).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid listed by type: " + listedBy);
        }
    }
}