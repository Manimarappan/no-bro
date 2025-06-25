package com.property.no_bro.dto.response;

import com.property.no_bro.enums.ListingType;
import com.property.no_bro.model.Address;
import com.property.no_bro.model.Listing;
import com.property.no_bro.model.Property;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ListingResponse {
    private String listingId;
    private String propertyId;
    private long addressId;
    private Long userId;
    private ListingType listingType;
    private LocalDate availableFrom;
    private boolean isPremium;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ListingResponse(Listing listing) {
        this.listingId = listing.getListingId();
        this.propertyId = listing.getPropertyId();
        this.addressId = listing.getAddressId();
        this.userId = listing.getUserId();
        this.listingType = listing.getListingType();
        this.availableFrom = listing.getAvailableFrom();
        this.isPremium = listing.isPremium();
        this.description = listing.getDescription();
        this.status = listing.getStatus();
        this.createdAt = listing.getCreatedAt();
        this.updatedAt = listing.getUpdatedAt();
    }
}