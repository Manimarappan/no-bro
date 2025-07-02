package com.property.no_bro.dto.response;

import com.property.no_bro.enums.PaymentMode;
import com.property.no_bro.enums.PaymentStatus;
import com.property.no_bro.model.Bill;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BillResponse {
    private Long billId;
    private String propertyId;
    private Double electricityBill;
    private Double waterBill;
    private Double maintenanceFee;
    private Double totalBill;
    private LocalDate billDate;
    private LocalDate dueDate;
    private LocalDateTime paidAt;
    private PaymentMode paymentMode;
    private PaymentStatus paymentStatus;

    public BillResponse(Bill bill) {
        this.billId = bill.getBillId();
        this.propertyId = (bill.getProperty() != null) ? bill.getProperty().getPropertyId() : null;
        this.electricityBill = bill.getElectricityBill();
        this.waterBill = bill.getWaterBill();
        this.maintenanceFee = bill.getMaintenanceFee();
        this.totalBill = bill.getTotalBill();
        this.billDate = bill.getBillDate();
        this.dueDate = bill.getDueDate();
        this.paidAt = bill.getPaidAt();
        this.paymentMode = bill.getPaymentMode();
        this.paymentStatus = bill.getPaymentStatus();
    }
}