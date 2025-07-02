package com.property.no_bro.dto.request;

import com.property.no_bro.enums.PaymentMode;
import com.property.no_bro.enums.PaymentStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BillRequest {
    @NotBlank(message = "Property ID is required")
    private String propertyId;

    @NotNull(message = "Electricity bill is required")
    @Min(value = 0, message = "Electricity bill cannot be negative")
    private Double electricityBill;

    @NotNull(message = "Water bill is required")
    @Min(value = 0, message = "Water bill cannot be negative")
    private Double waterBill;

    @NotNull(message = "Maintenance fee is required")
    @Min(value = 0, message = "Maintenance fee cannot be negative")
    private Double maintenanceFee;

    @NotNull(message = "Bill date is required")
    private LocalDate billDate;

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;

    @NotNull(message = "Payment mode is required")
    private PaymentMode paymentMode;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
}