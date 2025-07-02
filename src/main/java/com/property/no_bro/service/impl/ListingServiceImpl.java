package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.ListingRequest;
import com.property.no_bro.dto.response.ListingResponse;
import com.property.no_bro.model.Listing;
import com.property.no_bro.repository.ListingRepository;
import com.property.no_bro.service.ListingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingRepository listingRepository;

    @Override
    public ListingResponse createListing(ListingRequest listingRequest) {
        Listing listing = new Listing();
        listing.setPropertyId(listingRequest.getPropertyId());
        listing.setAddressId(listingRequest.getAddressId());
        listing.setUserId(listingRequest.getUserId());
        listing.setListingType(listingRequest.getListingType());
        listing.setAvailableFrom(listingRequest.getAvailableFrom());
        listing.setPremium(listingRequest.isPremium());
        boolean premiumValue = listingRequest.isPremium();
        System.out.println("isPremium value before setting: " + premiumValue); // Debug
        listing.setPremium(premiumValue);
        System.out.println("isPremium value after setting: " + listing.isPremium()); // Debug
        listing.setDescription(listingRequest.getDescription());
        listing.setStatus(listingRequest.getStatus());

        Listing savedListing = listingRepository.save(listing);
        System.out.println("isPremium value after save: " + savedListing.isPremium()); // Debug
        return new ListingResponse(savedListing);
    }

    @Override
    public ListingResponse getListingById(String listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found with ID: " + listingId));
        return new ListingResponse(listing);
    }

    @Override
    public List<ListingResponse> getAllListings() {
        return listingRepository.findAll().stream()
                .map(ListingResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponse> getListingsByPropertyId(String propertyId) {
        return listingRepository.findByPropertyId(propertyId).stream()
                .map(ListingResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponse> getListingsByStatus(String status) {
        return listingRepository.findByStatus(status).stream()
                .map(ListingResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponse> getListingsByAvailableFromAfter(LocalDate date) {
        return listingRepository.findByAvailableFromAfter(date).stream()
                .map(ListingResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponse> getListingsByIsPremiumTrue() {
        return listingRepository.findByIsPremiumTrue().stream()
                .map(ListingResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListingResponse> getListingsByPropertyIdAndStatus(String propertyId, String status) {
        return listingRepository.findByPropertyIdAndStatus(propertyId, status).stream()
                .map(ListingResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public ListingResponse updateListing(String listingId, ListingRequest listingRequest) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found with ID: " + listingId));

        listing.setPropertyId(listingRequest.getPropertyId());
        listing.setAddressId(listingRequest.getAddressId());
        listing.setUserId(listingRequest.getUserId());
        listing.setListingType(listingRequest.getListingType());
        listing.setAvailableFrom(listingRequest.getAvailableFrom());
        listing.setPremium(listingRequest.isPremium());
        listing.setDescription(listingRequest.getDescription());
        listing.setStatus(listingRequest.getStatus());

        Listing savedListing = listingRepository.save(listing);
        return new ListingResponse(savedListing);
    }

    @Override
    public void deleteListing(String listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found with ID: " + listingId));
        listingRepository.delete(listing);
    }
}