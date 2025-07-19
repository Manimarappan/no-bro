package com.property.no_bro.repository;

import com.property.no_bro.model.LikedProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedPropertyRepository extends JpaRepository<LikedProperty, Long> {
    Optional<LikedProperty> findByUserUserIdAndPropertyPropertyId(Long userId, String propertyId);
    Page<LikedProperty> findByUserUserId(Long userId, Pageable pageable);
    void deleteByUserUserIdAndPropertyPropertyId(Long userId, String propertyId);
}
