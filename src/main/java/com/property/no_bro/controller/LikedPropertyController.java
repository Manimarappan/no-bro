package com.property.no_bro.controller;

import com.property.no_bro.dto.ApiResponse;
import com.property.no_bro.dto.response.LikedPropertyResponse;
import com.property.no_bro.dto.response.PropertyResponse;
import com.property.no_bro.service.LikedPropertyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/liked-properties")
public class LikedPropertyController {

    @Autowired
    private LikedPropertyService likedPropertyService;


    @PostMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<LikedPropertyResponse>> likeProperty(
            @PathVariable String propertyId,
            @RequestParam Long userId
    ) {
        LikedPropertyResponse response = likedPropertyService.likeProperty(userId, propertyId);
        return ResponseEntity.ok(ApiResponse.success(response,"Liked successful", 200));
    }

    @Transactional
    @DeleteMapping("/{propertyId}")
    public ResponseEntity<ApiResponse<String>> unlikeProperty(
            @PathVariable String propertyId,
            @RequestParam Long userId
    ) {
        likedPropertyService.unlikeProperty(userId, propertyId);
        return ResponseEntity.ok(ApiResponse.success(null, "Property unliked successfully", 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PropertyResponse>>> getLikedProperties(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        Page<PropertyResponse> likedProperties = likedPropertyService.getLikedProperties(userId, page, pageSize);
        return ResponseEntity.ok(ApiResponse.success(likedProperties, "Liked properties retrieved successfully", 200));
    }
}
