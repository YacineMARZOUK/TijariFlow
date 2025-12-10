package org.example.tjariflow.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.tjariflow.model.enums.PaymentStatus;

@Data
public class PaymentRequestStatusDTO {
    @NotNull(message = "Payment status cannot be null")
    private PaymentStatus paymentStatus;
}

