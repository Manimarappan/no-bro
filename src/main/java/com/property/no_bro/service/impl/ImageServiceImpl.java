package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.ImageRequest;
import com.property.no_bro.dto.response.ImageResponse;
import com.property.no_bro.model.Image;

import com.property.no_bro.repository.ImageRepository;
import com.property.no_bro.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageResponse saveImage(ImageRequest request) {
        try {
            validateJpeg(request.getData());
            Image image = new Image();
            image.setData(request.getData().getBytes());
            Image savedImage = imageRepository.save(image);
            return new ImageResponse(savedImage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image data", e);
        }
    }

    @Override
    public ImageResponse getImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
        return new ImageResponse(image);
    }

    @Override
    public ImageResponse updateImage(Long id, ImageRequest request) {
        try {
            validateJpeg(request.getData());
            Image image = imageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
            image.setData(request.getData().getBytes());
            Image updatedImage = imageRepository.save(image);
            return new ImageResponse(updatedImage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image data", e);
        }
    }

    @Override
    public void deleteImage(Long id) {
        if (!imageRepository.existsById(id)) {
            throw new RuntimeException("Image not found with id: " + id);
        }
        imageRepository.deleteById(id);
    }

    private void validateJpeg(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty");
        }
        if (!"image/jpeg".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only JPEG files are allowed");
        }
    }
}