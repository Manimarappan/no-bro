//package com.property.no_bro.dto.request;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.property.no_bro.enums.ListingType;
//import jakarta.validation.constraints.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Data
//@NoArgsConstructor
//public class ListingRequest {
//    @NotBlank(message = "Property ID is required")
//    private String propertyId;
//
//    @NotNull(message = "Address ID is required")
//    private Long addressId;
//
//    @NotNull(message = "User ID is required")
//    private Long userId;
//
//    @NotNull(message = "Listing type is required")
//    private ListingType listingType;
//
//    @NotNull(message = "Available from date is required")
//    @FutureOrPresent(message = "Available from date must be today or in the future")
//    private LocalDate availableFrom;
//
//    @JsonProperty("isPremium")
//    private boolean isPremium;
//
//    private String description;
//
//    @NotBlank(message = "Status is required")
//    private String status;
//
//}