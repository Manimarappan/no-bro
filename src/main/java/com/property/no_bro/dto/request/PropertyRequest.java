package com.property.no_bro.dto.request;

import com.property.no_bro.enums.Furnishing;
import com.property.no_bro.enums.ListedBy;
import com.property.no_bro.enums.PropertyType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PropertyRequest {
    @NotBlank(message = "Property name is required")
    private String propertyName;

    @NotNull(message = "Property type is required")
    private PropertyType propertyType;

    @NotNull(message = "Furnishing is required")
    private Furnishing furnishing;

    @NotBlank(message = "Status is required")
    private String status;

    @Positive(message = "Price must be positive")
    private double price;

    @Positive(message = "Area must be positive")
    private double area;

    @Min(value = 0, message = "Bedrooms cannot be negative")
    private int bedrooms;

    @Min(value = 0, message = "Bathrooms cannot be negative")
    private int bathrooms;

    @Min(value = 0, message = "Balconies cannot be negative")
    private int balconies;

    @Min(value = 0, message = "Floor number cannot be negative")
    private int floorNumber;

    @Min(value = 0, message = "Total floors cannot be negative")
    private int totalFloors;

    private boolean parking;

    @Min(value = 1900, message = "Year built must be after 1900")
    private int yearBuilt;

    @NotNull(message = "Listed by is required")
    private ListedBy listedBy;

    @NotNull(message = "User ID is required")
    private Long userId; // Reference to User ID to avoid circular references

    @NotNull(message = "Image ID is required")
    private Long imageId;

    private boolean isFeatured;

}