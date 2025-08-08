package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.PropertyRequest;
import com.property.no_bro.dto.response.LikedPropertyResponse;
import com.property.no_bro.dto.response.PropertyResponse;
import com.property.no_bro.model.Address;
import com.property.no_bro.model.Image;
import com.property.no_bro.model.Property;
import com.property.no_bro.model.User;
import com.property.no_bro.repository.*;
import com.property.no_bro.service.PropertyService;
import com.property.no_bro.enums.ListedBy;
import com.property.no_bro.enums.PropertyType;
import com.property.no_bro.exception.InvalidInputException;
import com.property.no_bro.exception.ResourceNotFoundException;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private LikedPropertyRepository likedPropertyRepository;

    @Override
    public PropertyResponse saveProperty(PropertyRequest propertyRequest) {
        if (propertyRequest.getUserId() == null) {
            throw new InvalidInputException("User ID is required");
        }
        User user = userRepository.findById(propertyRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + propertyRequest.getUserId()));

        Address address = addressRepository.findById(propertyRequest.getAddress())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + propertyRequest.getAddress()));

        Property property = new Property();
        property.setPropertyName(propertyRequest.getPropertyName());
        property.setPropertyType(propertyRequest.getPropertyType());
        property.setAddress(address);
        property.setBhk(propertyRequest.getBhk());
        property.setFurnishing(propertyRequest.getFurnishing());
        property.setStatus(propertyRequest.getStatus());
        property.setPrice(propertyRequest.getPrice());
        property.setDeposit(propertyRequest.getDeposit());
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
        if (propertyRequest.getImageId() != null) {
            Image image = imageRepository.findById(propertyRequest.getImageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + propertyRequest.getImageId()));
            property.setImage(image);
        }
        property.setFeatured(propertyRequest.isFeatured());
        property.setDescription(propertyRequest.getDescription());
        property.setViewsCount(0);
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
        if (propertyDetails.getBhk() != null) property.setBhk(propertyDetails.getBhk());
        if (propertyDetails.getFurnishing() != null) property.setFurnishing(propertyDetails.getFurnishing());
        if (propertyDetails.getStatus() != null) property.setStatus(propertyDetails.getStatus());
        if (propertyDetails.getPrice() > 0) property.setPrice(propertyDetails.getPrice());
        if (propertyDetails.getDeposit() > 0) property.setDeposit(propertyDetails.getDeposit());
        if (propertyDetails.getArea() > 0) property.setArea(propertyDetails.getArea());
        if (propertyDetails.getBedrooms() >= 0) property.setBedrooms(propertyDetails.getBedrooms());
        if (propertyDetails.getBathrooms() >= 0) property.setBathrooms(propertyDetails.getBathrooms());
        if (propertyDetails.getBalconies() >= 0) property.setBalconies(propertyDetails.getBalconies());
        if (propertyDetails.getFloorNumber() >= 0) property.setFloorNumber(propertyDetails.getFloorNumber());
        if (propertyDetails.getTotalFloors() >= 0) property.setTotalFloors(propertyDetails.getTotalFloors());
        if (propertyDetails.isParking()) property.setParking(propertyDetails.isParking());
        if (propertyDetails.getYearBuilt() >= 1900) property.setYearBuilt(propertyDetails.getYearBuilt());
        if (propertyDetails.getListedBy() != null) property.setListedBy(propertyDetails.getListedBy());
        if (propertyDetails.getImageId() != null) {
            Image image = imageRepository.findById(propertyDetails.getImageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + propertyDetails.getImageId()));
            property.setImage(image);
        }

        if (propertyDetails.getAddress() != 0) { // Optional: Check if address ID is not 0, if 0 is invalid
            Address address = addressRepository.findById(propertyDetails.getAddress())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + propertyDetails.getAddress()));
            property.setAddress(address); // Use setPropertyAddress as per your Property entity
        }

        property.setFeatured(propertyDetails.isFeatured());

        if(propertyDetails.getDescription() != null) property.setDescription(propertyDetails.getDescription());

        property.setUpdatedAt(LocalDateTime.now());

        Property updatedProperty = propertyRepository.save(property);
        return new PropertyResponse(updatedProperty);
    }


    @Override
    public void deleteProperty(String propertyId) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new ResourceNotFoundException("Property not found with id: " + propertyId);
        }
        likedPropertyRepository.deleteByProperty_PropertyId(propertyId);
        propertyRepository.deleteById(propertyId);
    }

//    @Override
//    public List<PropertyResponse> getAllProperties() {
//        return propertyRepository.findAll().stream().map(PropertyResponse::new).collect(Collectors.toList());
//    }

    @Override
    public Page<PropertyResponse> getAllProperties(Pageable pageable) {
        return propertyRepository.findAll(pageable).map(PropertyResponse::new);
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
            return propertyRepository.findByFurnishing(furnishing).stream()
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

    @Override
    public List<PropertyResponse> getPropertiesByBhk(String bhk) {
        try {
            List<Property> properties = propertyRepository.findByBhk(bhk);
            return properties.stream()
                    .map(PropertyResponse::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Error fetching properties for bhk: " + bhk);
        }
    }

    @Override
    public Page<PropertyResponse> searchProperties(
            String propertyType,
            String bhk,
            String furnishing,
            double rent,
            String city,
            Pageable pageable
    ) {
        Specification<Property> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!propertyType.isEmpty()) {
                try {
                    predicates.add(cb.equal(root.<PropertyType>get("propertyType"), PropertyType.valueOf(propertyType.toUpperCase())));
                } catch (IllegalArgumentException e) {
                    // Ignore invalid enum value
                }
            }
            if (!bhk.isEmpty()) {
                predicates.add(cb.equal(root.<String>get("bhk"), bhk));
            }
            if (!furnishing.isEmpty()) {
                try {
                    predicates.add(cb.equal(root.<String>get("furnishing"), furnishing));
                } catch (IllegalArgumentException e) {
                    // Ignore invalid enum value
                }
            }
            if (rent > 0) {
                predicates.add(cb.lessThanOrEqualTo(root.<Double>get("price"), rent));
            }
            if (!city.isEmpty()) {
                Join<Property, Address> addressJoin = root.join("address");
                predicates.add(cb.equal(cb.lower(addressJoin.<String>get("city")), city.toLowerCase()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return propertyRepository.findAll(spec, pageable)
                .map(this::convertToPropertyResponse);
    }

    private PropertyResponse convertToPropertyResponse(Property property) {
        return new PropertyResponse(property);
    }

    @Override
    public List<PropertyResponse> getUserId(long userId){
        try {
            List<Property> properties = propertyRepository.findByUserUserId(userId);
            return properties.stream()
                    .map(PropertyResponse::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Error fetching properties for userId: " + userId);
        }
    }


}