package com.property.no_bro.dto.response;

import com.property.no_bro.model.Image;
import lombok.Data;

@Data
public class ImageResponse {
    private Long id;
    private byte[] data;

    public ImageResponse(Image image) {
        this.id = image.getId();
        this.data = image.getData();
    }
}
