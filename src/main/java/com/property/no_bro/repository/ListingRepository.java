//package com.property.no_bro.repository;
//
//import com.property.no_bro.model.Listing;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ListingRepository extends JpaRepository<Listing, String> {
//
//    List<Listing> findByPropertyId(String propertyId);
//
//    List<Listing> findByStatus(String status);
//
//    List<Listing> findByAvailableFromAfter(LocalDate date);
//
//    List<Listing> findByIsPremiumTrue();
//
//    @Query("SELECT l FROM Listing l WHERE l.propertyId = :propertyId AND l.status = :status")
//    List<Listing> findByPropertyIdAndStatus(String propertyId, String status);
//}