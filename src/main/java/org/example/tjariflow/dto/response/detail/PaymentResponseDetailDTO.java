package org.example.tjariflow.dto.response.detail;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.example.tjariflow.dto.response.basic.OrderResponseBasicAdminDTO;
import org.example.tjariflow.model.enums.PaymentStatus;
import org.example.tjariflow.model.enums.PaymentType;

import java.math.BigDecimal;

@Data
public class PaymentResponseDetailDTO {
    private String id;
    private OrderResponseBasicAdminDTO order;
    private PaymentType type;
    private PaymentStatus status;
    private BigDecimal amount;
    @JsonFormat(pattern = "dd MMMM yyyy ", locale = "fr")
    private String datePayment;
    @JsonFormat(pattern = "dd MMMM yyyy ", locale = "fr")
    private String dateDeposit;
}
