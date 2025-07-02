package com.property.no_bro.service;

import com.property.no_bro.dto.request.PropertyEnquiryRequest;
import com.property.no_bro.dto.response.PropertyEnquiryResponse;
import com.property.no_bro.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyEnquiryService {
    PropertyEnquiryResponse createEnquiry(PropertyEnquiryRequest enquiryRequest);

    PropertyEnquiryResponse getEnquiryById(Long enquiryId);

    Page<PropertyEnquiryResponse> getAllEnquiries(Pageable pageable);

    List<PropertyEnquiryResponse> getEnquiriesByStatus(Status status); // Added method

    PropertyEnquiryResponse updateEnquiry(Long enquiryId, PropertyEnquiryRequest enquiryRequest);

    void deleteEnquiry(Long enquiryId);
}