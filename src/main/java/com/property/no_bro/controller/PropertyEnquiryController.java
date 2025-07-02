package com.property.no_bro.controller;

import com.property.no_bro.dto.request.PropertyEnquiryRequest;
import com.property.no_bro.dto.response.PropertyEnquiryResponse;
import com.property.no_bro.enums.Status;
import com.property.no_bro.service.PropertyEnquiryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
public class PropertyEnquiryController {

    @Autowired
    private PropertyEnquiryService enquiryService;

    @PostMapping
    public ResponseEntity<PropertyEnquiryResponse> createEnquiry(@Valid @RequestBody PropertyEnquiryRequest enquiryRequest) {
        return ResponseEntity.ok(enquiryService.createEnquiry(enquiryRequest));
    }

    @GetMapping("/{enquiryId}")
    public ResponseEntity<PropertyEnquiryResponse> getEnquiryById(@PathVariable Long enquiryId) {
        return ResponseEntity.ok(enquiryService.getEnquiryById(enquiryId));
    }

    @GetMapping
    public ResponseEntity<Page<PropertyEnquiryResponse>> getAllEnquiries(Pageable pageable) {
        return ResponseEntity.ok(enquiryService.getAllEnquiries(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PropertyEnquiryResponse>> getEnquiriesByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(enquiryService.getEnquiriesByStatus(status));
    }

    @PutMapping("/{enquiryId}")
    public ResponseEntity<PropertyEnquiryResponse> updateEnquiry(@PathVariable Long enquiryId, @Valid @RequestBody PropertyEnquiryRequest enquiryRequest) {
        return ResponseEntity.ok(enquiryService.updateEnquiry(enquiryId, enquiryRequest));
    }

    @DeleteMapping("/{enquiryId}")
    public ResponseEntity<Void> deleteEnquiry(@PathVariable Long enquiryId) {
        enquiryService.deleteEnquiry(enquiryId);
        return ResponseEntity.noContent().build();
    }
}