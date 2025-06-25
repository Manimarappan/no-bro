package com.property.no_bro.service;

import com.property.no_bro.dto.request.BillRequest;
import com.property.no_bro.dto.response.BillResponse;
import com.property.no_bro.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillService {
    BillResponse createBill(BillRequest billRequest);

    BillResponse getBillById(Long billId);

    Page<BillResponse> getAllBills(Pageable pageable);

    List<BillResponse> getBillsByPaymentStatus(PaymentStatus paymentStatus);

    BillResponse updateBill(Long billId, BillRequest billRequest);

    void deleteBill(Long billId);
}