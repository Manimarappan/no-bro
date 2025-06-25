package com.property.no_bro.dto.request;

import com.property.no_bro.enums.ContactMode;
import com.property.no_bro.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PropertyEnquiryRequest {
    @NotBlank(message = "Property ID is required")
    private String propertyId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Message is required")
    private String message;

    @NotNull(message = "Contact mode is required")
    private ContactMode contactMode;

    @NotNull(message = "Status is required")
    private Status status;
}