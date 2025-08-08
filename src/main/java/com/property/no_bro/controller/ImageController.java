package com.property.no_bro.controller;

import com.property.no_bro.dto.ApiResponse;
import com.property.no_bro.dto.request.ImageRequest;
import com.property.no_bro.dto.response.AddressResponse;
import com.property.no_bro.dto.response.ImageResponse;
import com.property.no_bro.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse<ImageResponse>> createImage(@ModelAttribute ImageRequest request) {
        ImageResponse response = imageService.saveImage(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Image Added successfully", 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ImageResponse>> getImage(@PathVariable Long id) {
        ImageResponse response = imageService.getImage(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Image fetched successfully", 200));
    }

    @GetMapping("/{id}/data")
    public ResponseEntity<byte[]> getImageData(@PathVariable Long id) {
        ImageResponse response = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(response.getData());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageResponse> updateImage(@PathVariable Long id, @RequestBody ImageRequest request) {
        ImageResponse response = imageService.updateImage(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}