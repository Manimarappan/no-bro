package com.property.no_bro.service;


import com.property.no_bro.dto.request.ImageRequest;
import com.property.no_bro.dto.response.ImageResponse;

public interface ImageService {
    ImageResponse saveImage(ImageRequest request);
    ImageResponse getImage(Long id);
    ImageResponse updateImage(Long id, ImageRequest request);
    void deleteImage(Long id);
}