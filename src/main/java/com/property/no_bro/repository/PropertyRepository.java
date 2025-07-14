package com.property.no_bro.repository;

import com.property.no_bro.model.Property;
import com.property.no_bro.enums.Furnishing;
import com.property.no_bro.enums.ListedBy;
import com.property.no_bro.enums.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, String> {
    List<Property> findByPropertyType(PropertyType propertyType);
    List<Property> findByFurnishing(Furnishing furnishing);
    List<Property> findByPriceBetween(double minPrice, double maxPrice);
    List<Property> findByBedroomsGreaterThanEqual(int bedrooms);
    List<Property> findByStatus(String status);
    List<Property> findByListedBy(ListedBy listedBy);

    List<Property> findByBhk(String type);
}