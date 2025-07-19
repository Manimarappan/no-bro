package com.property.no_bro.dto.response;


import com.property.no_bro.model.LikedProperty;
import lombok.Data;

@Data
public class LikedPropertyResponse {
    private long id;
    private long userId;
    private String propertyId;

    public LikedPropertyResponse(LikedProperty likedProperty){
        this.id = likedProperty.getId();
        this.userId = likedProperty.getUser().getUserId();
        this.propertyId = likedProperty.getProperty().getPropertyId();
    }
}
