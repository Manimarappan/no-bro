package com.property.no_bro.controller;

import com.property.no_bro.dto.request.BillRequest;
import com.property.no_bro.dto.response.BillResponse;
import com.property.no_bro.enums.PaymentStatus;
import com.property.no_bro.service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@Valid @RequestBody BillRequest billRequest) {
        return ResponseEntity.ok(billService.createBill(billRequest));
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long billId) {
        return ResponseEntity.ok(billService.getBillById(billId));
    }

    @GetMapping
    public ResponseEntity<Page<BillResponse>> getAllBills(Pageable pageable) {
        return ResponseEntity.ok(billService.getAllBills(pageable));
    }

    @GetMapping("/status/{paymentStatus}")
    public ResponseEntity<List<BillResponse>> getBillsByPaymentStatus(@PathVariable PaymentStatus paymentStatus) {
        return ResponseEntity.ok(billService.getBillsByPaymentStatus(paymentStatus));
    }

    @PutMapping("/{billId}")
    public ResponseEntity<BillResponse> updateBill(@PathVariable Long billId, @Valid @RequestBody BillRequest billRequest) {
        return ResponseEntity.ok(billService.updateBill(billId, billRequest));
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long billId) {
        billService.deleteBill(billId);
        return ResponseEntity.noContent().build();
    }
}