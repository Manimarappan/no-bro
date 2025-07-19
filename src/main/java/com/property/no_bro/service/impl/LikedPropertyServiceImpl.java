package com.property.no_bro.service.impl;

import com.property.no_bro.dto.response.LikedPropertyResponse;
import com.property.no_bro.dto.response.PropertyResponse;
import com.property.no_bro.model.LikedProperty;
import com.property.no_bro.model.Property;
import com.property.no_bro.model.User;
import com.property.no_bro.exception.InvalidInputException;
import com.property.no_bro.repository.LikedPropertyRepository;
import com.property.no_bro.repository.PropertyRepository;
import com.property.no_bro.repository.UserRepository;
import com.property.no_bro.service.LikedPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikedPropertyServiceImpl implements LikedPropertyService {
    
    @Autowired
    private LikedPropertyRepository likedPropertyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;


    @Override
    public LikedPropertyResponse likeProperty(Long userId, String propertyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidInputException("User not found"));
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new InvalidInputException("Property not found"));

        if (likedPropertyRepository.findByUserUserIdAndPropertyPropertyId(userId, propertyId).isPresent()) {
            throw new InvalidInputException("Property already liked");
        }

        LikedProperty likedProperty = new LikedProperty();
        likedProperty.setUser(user);
        likedProperty.setProperty(property);
        likedProperty.setLikedAt(LocalDateTime.now());
        LikedProperty savedLikedProperty = likedPropertyRepository.save(likedProperty);
        return new LikedPropertyResponse(savedLikedProperty);
    }

    @Override
    public void unlikeProperty(Long userId, String propertyId) {
        if (!likedPropertyRepository.findByUserUserIdAndPropertyPropertyId(userId, propertyId).isPresent()) {
            throw new InvalidInputException("Property not liked");
        }
        likedPropertyRepository.deleteByUserUserIdAndPropertyPropertyId(userId, propertyId);
    }

    @Override
    public Page<PropertyResponse> getLikedProperties(Long userId, int page, int pageSize) {
        if (!userRepository.existsById(userId)) {
            throw new InvalidInputException("User not found");
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<LikedProperty> likedProperties = likedPropertyRepository.findByUserUserId(userId, pageable);
        return likedProperties.map(likedProperty -> new PropertyResponse(likedProperty.getProperty()));
    }
}
