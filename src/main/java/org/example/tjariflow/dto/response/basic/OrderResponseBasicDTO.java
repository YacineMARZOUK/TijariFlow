package org.example.tjariflow.dto.response.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.tjariflow.model.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
public class OrderResponseBasicDTO {
    @JsonFormat(pattern = "dd MMMM yyyy ", locale = "fr")
    private LocalDate date ;
    private BigDecimal subTotal;
    private BigDecimal discount;
    private BigDecimal TVA;
    private BigDecimal totalTTC;
    private BigDecimal remainingAmount;
    private String codePromo;
    private OrderStatus status;
    private List<OrderItemResponseBasicDTO> orderItems;
}