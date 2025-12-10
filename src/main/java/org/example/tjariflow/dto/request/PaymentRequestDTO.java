package org.example.tjariflow.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.example.tjariflow.model.enums.PaymentType;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "Order ID cannot be null")
    private String orderId;
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    @NotNull(message = "Payment type cannot be null")
    private PaymentType type;
}