package com.property.no_bro.dto.response;

import com.property.no_bro.enums.ContactMode;
import com.property.no_bro.enums.Status;
import com.property.no_bro.model.PropertyEnquiry;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PropertyEnquiryResponse {
    private Long enquiryId;
    private String propertyId;
    private Long userId;
    private String message;
    private ContactMode contactMode;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime responseDate;

    public PropertyEnquiryResponse(PropertyEnquiry enquiry) {
        this.enquiryId = enquiry.getEnquiryId();
        this.propertyId = (enquiry.getProperty() != null) ? enquiry.getProperty().getPropertyId() : null;
        this.userId = (enquiry.getUser() != null) ? enquiry.getUser().getUserId() : null;
        this.message = enquiry.getMessage();
        this.contactMode = enquiry.getContactMode();
        this.status = enquiry.getStatus();
        this.createdAt = enquiry.getCreatedAt();
        this.responseDate = enquiry.getResponseDate();
    }
}