package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.BillRequest;
import com.property.no_bro.dto.response.BillResponse;
import com.property.no_bro.enums.PaymentStatus;
import com.property.no_bro.model.Bill;
import com.property.no_bro.model.Property;
import com.property.no_bro.repository.BillRepository;
import com.property.no_bro.service.BillService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Override
    public BillResponse createBill(BillRequest billRequest) {
        Bill bill = new Bill();
        bill.setProperty(new Property());
        bill.getProperty().setPropertyId(billRequest.getPropertyId());
        bill.setElectricityBill(billRequest.getElectricityBill());
        bill.setWaterBill(billRequest.getWaterBill());
        bill.setMaintenanceFee(billRequest.getMaintenanceFee());
        bill.setTotalBill(billRequest.getElectricityBill() + billRequest.getWaterBill() + billRequest.getMaintenanceFee());
        bill.setBillDate(billRequest.getBillDate());
        bill.setDueDate(billRequest.getDueDate());
        bill.setPaymentMode(billRequest.getPaymentMode());
        bill.setPaymentStatus(billRequest.getPaymentStatus());
        Bill savedBill = billRepository.save(bill);
        return new BillResponse(savedBill);
    }

    @Override
    public BillResponse getBillById(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found with ID: " + billId));
        return new BillResponse(bill);
    }

    @Override
    public Page<BillResponse> getAllBills(Pageable pageable) {
        return billRepository.findAll(pageable).map(BillResponse::new);
    }

    @Override
    public List<BillResponse> getBillsByPaymentStatus(PaymentStatus paymentStatus) {
        List<Bill> bills = billRepository.findByPaymentStatus(paymentStatus);
        return bills.stream()
                .map(BillResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public BillResponse updateBill(Long billId, BillRequest billRequest) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found with ID: " + billId));

        bill.setProperty(new Property());
        bill.getProperty().setPropertyId(billRequest.getPropertyId());
        bill.setElectricityBill(billRequest.getElectricityBill());
        bill.setWaterBill(billRequest.getWaterBill());
        bill.setMaintenanceFee(billRequest.getMaintenanceFee());
        bill.setTotalBill(billRequest.getElectricityBill() + billRequest.getWaterBill() + billRequest.getMaintenanceFee());
        bill.setBillDate(billRequest.getBillDate());
        bill.setDueDate(billRequest.getDueDate());
        bill.setPaymentMode(billRequest.getPaymentMode());
        bill.setPaymentStatus(billRequest.getPaymentStatus());

        Bill savedBill = billRepository.save(bill);
        return new BillResponse(savedBill);
    }

    @Override
    public void deleteBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found with ID: " + billId));
        billRepository.delete(bill);
    }
}