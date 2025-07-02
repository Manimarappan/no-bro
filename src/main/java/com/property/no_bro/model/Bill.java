package com.property.no_bro.model;

import com.property.no_bro.enums.PaymentMode;
import com.property.no_bro.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "propertyId", referencedColumnName = "propertyId")
    private Property property;

    private double electricityBill;
    private double waterBill;
    private double maintenanceFee;
    private double totalBill;//sum of electricitybill+waterBill+maintenanceFee
    private LocalDate billDate;
    private LocalDate dueDate;

    @Column(nullable = true)
    private LocalDateTime paidAt;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    @PrePersist
    @PreUpdate
    protected void onSaveOrUpdate() {
        totalBill = (electricityBill + waterBill + maintenanceFee);
        if (paymentStatus == PaymentStatus.PAID && paidAt == null) {
            paidAt = LocalDateTime.now();
        }
    }

}
