package com.property.no_bro.repository;

import com.property.no_bro.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Find address by property ID
//    Optional<Address> findByPropertyPropertyId(String propertyId);

    // Find addresses by city
    List<Address> findByCity(String city);

    // Find addresses by city and state
    List<Address> findByCityAndState(String city, String state);
}