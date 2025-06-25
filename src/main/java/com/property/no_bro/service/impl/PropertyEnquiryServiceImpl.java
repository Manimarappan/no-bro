package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.PropertyEnquiryRequest;
import com.property.no_bro.dto.response.PropertyEnquiryResponse;
import com.property.no_bro.enums.Status;
import com.property.no_bro.model.Property;
import com.property.no_bro.model.PropertyEnquiry;
import com.property.no_bro.model.User;
import com.property.no_bro.repository.PropertyEnquiryRepository;
import com.property.no_bro.service.PropertyEnquiryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyEnquiryServiceImpl implements PropertyEnquiryService {

    @Autowired
    private PropertyEnquiryRepository enquiryRepository;

    @Override
    public PropertyEnquiryResponse createEnquiry(PropertyEnquiryRequest enquiryRequest) {
        PropertyEnquiry enquiry = new PropertyEnquiry();
        enquiry.setProperty(new Property());
        enquiry.getProperty().setPropertyId(enquiryRequest.getPropertyId());
        enquiry.setUser(new User());
        enquiry.getUser().setUserId(enquiryRequest.getUserId());
        enquiry.setMessage(enquiryRequest.getMessage());
        enquiry.setContactMode(enquiryRequest.getContactMode());
        enquiry.setStatus(enquiryRequest.getStatus());
        PropertyEnquiry savedEnquiry = enquiryRepository.save(enquiry);
        return new PropertyEnquiryResponse(savedEnquiry);
    }

    @Override
    public PropertyEnquiryResponse getEnquiryById(Long enquiryId) {
        PropertyEnquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() -> new EntityNotFoundException("Enquiry not found with ID: " + enquiryId));
        return new PropertyEnquiryResponse(enquiry);
    }

    @Override
    public Page<PropertyEnquiryResponse> getAllEnquiries(Pageable pageable) {
        return enquiryRepository.findAll(pageable).map(PropertyEnquiryResponse::new);
    }

    @Override
    public List<PropertyEnquiryResponse> getEnquiriesByStatus(Status status) {
        List<PropertyEnquiry> enquiries = enquiryRepository.findByStatus(status);
        return enquiries.stream()
                .map(PropertyEnquiryResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyEnquiryResponse updateEnquiry(Long enquiryId, PropertyEnquiryRequest enquiryRequest) {
        PropertyEnquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() -> new EntityNotFoundException("Enquiry not found with ID: " + enquiryId));

        enquiry.setProperty(new Property());
        enquiry.getProperty().setPropertyId(enquiryRequest.getPropertyId());
        enquiry.setUser(new User());
        enquiry.getUser().setUserId(enquiryRequest.getUserId());
        enquiry.setMessage(enquiryRequest.getMessage());
        enquiry.setContactMode(enquiryRequest.getContactMode());
        enquiry.setStatus(enquiryRequest.getStatus());

        PropertyEnquiry savedEnquiry = enquiryRepository.save(enquiry);
        return new PropertyEnquiryResponse(savedEnquiry);
    }

    @Override
    public void deleteEnquiry(Long enquiryId) {
        PropertyEnquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() -> new EntityNotFoundException("Enquiry not found with ID: " + enquiryId));
        enquiryRepository.delete(enquiry);
    }
}