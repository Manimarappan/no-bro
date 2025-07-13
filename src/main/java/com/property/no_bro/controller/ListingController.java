//package com.property.no_bro.controller;
//
//import com.property.no_bro.dto.request.ListingRequest;
//import com.property.no_bro.dto.response.ListingResponse;
//import com.property.no_bro.service.ListingService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/listings")
//public class ListingController {
//
//    @Autowired
//    private ListingService listingService;
//
//    @PostMapping
//    public ResponseEntity<ListingResponse> createListing(@Valid @RequestBody ListingRequest listingRequest) {
//        return ResponseEntity.ok(listingService.createListing(listingRequest));
//    }
//
//    @GetMapping("/{listingId}")
//    public ResponseEntity<ListingResponse> getListingById(@PathVariable String listingId) {
//        return ResponseEntity.ok(listingService.getListingById(listingId));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ListingResponse>> getAllListings(
//            @RequestParam(required = false) String propertyId,
//            @RequestParam(required = false) String status,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate availableFromAfter,
//            @RequestParam(required = false) Boolean isPremium) {
//        if (propertyId != null && status != null) {
//            return ResponseEntity.ok(listingService.getListingsByPropertyIdAndStatus(propertyId, status));
//        } else if (propertyId != null) {
//            return ResponseEntity.ok(listingService.getListingsByPropertyId(propertyId));
//        } else if (status != null) {
//            return ResponseEntity.ok(listingService.getListingsByStatus(status));
//        } else if (availableFromAfter != null) {
//            return ResponseEntity.ok(listingService.getListingsByAvailableFromAfter(availableFromAfter));
//        } else if (isPremium != null && isPremium) {
//            return ResponseEntity.ok(listingService.getListingsByIsPremiumTrue());
//        }
//        return ResponseEntity.ok(listingService.getAllListings());
//    }
//
//    @PutMapping("/{listingId}")
//    public ResponseEntity<ListingResponse> updateListing(
//            @PathVariable String listingId, @Valid @RequestBody ListingRequest listingRequest) {
//        return ResponseEntity.ok(listingService.updateListing(listingId, listingRequest));
//    }
//
//    @DeleteMapping("/{listingId}")
//    public ResponseEntity<Void> deleteListing(@PathVariable String listingId) {
//        listingService.deleteListing(listingId);
//        return ResponseEntity.noContent().build();
//    }
//}