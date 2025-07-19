package com.property.no_bro.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "liked_properties")
public class LikedProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "propertyId", nullable = false)
    private Property property;

    @Column(name = "liked_at")
    private java.time.LocalDateTime likedAt;
}
