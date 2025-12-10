package org.example.tjariflow.dto.response.basic;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.example.tjariflow.model.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class OrderResponseBasicAdminDTO {
    private String id;
    @JsonFormat(pattern = "dd MMMM yyyy ", locale = "fr")
    private LocalDate date ;
    private BigDecimal TVA;
    private BigDecimal totalTTC;
    private BigDecimal remainingAmount;
    private OrderStatus status;
    private ClientResponseBasicDTO client;
}
