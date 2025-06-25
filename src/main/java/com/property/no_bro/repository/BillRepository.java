package com.property.no_bro.repository;

import com.property.no_bro.model.Bill;
import com.property.no_bro.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByPaymentStatus(PaymentStatus paymentStatus);
}