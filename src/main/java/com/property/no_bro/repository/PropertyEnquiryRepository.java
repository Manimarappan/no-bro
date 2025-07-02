package com.property.no_bro.repository;

import com.property.no_bro.enums.Status;
import com.property.no_bro.model.PropertyEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyEnquiryRepository extends JpaRepository<PropertyEnquiry, Long> {
    List<PropertyEnquiry> findByStatus(Status status);
}