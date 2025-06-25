package com.property.no_bro.service;

import com.property.no_bro.dto.request.ListingRequest;
import com.property.no_bro.dto.response.ListingResponse;

import java.time.LocalDate;
import java.util.List;

public interface ListingService {
    ListingResponse createListing(ListingRequest listingRequest);

    ListingResponse getListingById(String listingId);

    List<ListingResponse> getAllListings();

    List<ListingResponse> getListingsByPropertyId(String propertyId);

    List<ListingResponse> getListingsByStatus(String status);

    List<ListingResponse> getListingsByAvailableFromAfter(LocalDate date);

    List<ListingResponse> getListingsByIsPremiumTrue();

    List<ListingResponse> getListingsByPropertyIdAndStatus(String propertyId, String status);

    ListingResponse updateListing(String listingId, ListingRequest listingRequest);

    void deleteListing(String listingId);
}