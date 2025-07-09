package com.property.no_bro.dto.response;

import com.property.no_bro.enums.Furnishing;
import com.property.no_bro.enums.ListedBy;
import com.property.no_bro.enums.PropertyType;
import com.property.no_bro.model.Property;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PropertyResponse {
    private String propertyId;
    private String propertyName;
    private PropertyType propertyType;
    private Furnishing furnishing;
    private String status;
    private double price;
    private double deposit;
    private double area;
    private int bedrooms;
    private int bathrooms;
    private int balconies;
    private int floorNumber;
    private int totalFloors;
    private boolean parking;
    private int yearBuilt;
    private ListedBy listedBy;
    private Long userId; // Instead of full User object
    private Long imageId;
    private boolean isFeatured;
    private int viewsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isAvailable;

    public PropertyResponse(Property property) {
        this.propertyId = property.getPropertyId();
        this.propertyName = property.getPropertyName();
        this.propertyType = property.getPropertyType();
        this.furnishing = property.getFurnishing();
        this.status = property.getStatus();
        this.price = property.getPrice();
        this.deposit = property.getDeposit();
        this.area = property.getArea();
        this.bedrooms = property.getBedrooms();
        this.bathrooms = property.getBathrooms();
        this.balconies = property.getBalconies();
        this.floorNumber = property.getFloorNumber();
        this.totalFloors = property.getTotalFloors();
        this.parking = property.isParking();
        this.yearBuilt = property.getYearBuilt();
        this.listedBy = property.getListedBy();
        this.userId = (property.getUser() != null) ? property.getUser().getUserId() : null;
        this.imageId = property.getImage() != null ? property.getImage().getId() : null;
        this.isFeatured = property.isFeatured();
        this.viewsCount = property.getViewsCount();
        this.createdAt = property.getCreatedAt();
        this.updatedAt = property.getUpdatedAt();
    }
}