package com.property.no_bro.service;

import com.property.no_bro.dto.response.LikedPropertyResponse;
import com.property.no_bro.dto.response.PropertyResponse;
import org.springframework.data.domain.Page;

public interface LikedPropertyService {
    LikedPropertyResponse likeProperty(Long userId, String propertyId);
    void unlikeProperty(Long userId, String propertyId);
    Page<PropertyResponse> getLikedProperties(Long userId, int page, int pageSize);
}
