package com.property.no_bro.model;

import com.property.no_bro.enums.ListingType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String listingId;

    @Column(name = "property_id")
    private String propertyId;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    private LocalDate availableFrom;
    private boolean isPremium;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}