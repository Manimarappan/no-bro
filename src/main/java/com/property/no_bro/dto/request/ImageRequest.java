package com.property.no_bro.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageRequest {
    private MultipartFile data;
}