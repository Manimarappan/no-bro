package com.property.no_bro.model;

import com.property.no_bro.enums.Furnishing;
import com.property.no_bro.enums.ListedBy;
import com.property.no_bro.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String propertyId;

    private String propertyName;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private Furnishing furnishing;

    private String bhk;

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

    @Enumerated(EnumType.STRING)
    private ListedBy listedBy;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "imageId", referencedColumnName = "id")
    private Image image;

    private boolean isFeatured;
    private int viewsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
