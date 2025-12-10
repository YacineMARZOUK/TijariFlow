package org.example.tjariflow.dto.response.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.tjariflow.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDetailDTO {
    private String id;
    @JsonFormat(pattern = "dd MMMM yyyy ", locale = "fr")
    private LocalDate date ;
    private BigDecimal subTotal;
    private BigDecimal discount;
    private BigDecimal TVA;
    private BigDecimal totalTTC;
    private BigDecimal remainingAmount;
    private String codePromo;
    private OrderStatus status;
    private ClientResponseDetailDTO client;
    private List<OrderItemResponseDetailDTO> orderItems;
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", locale = "fr")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", locale = "fr")
    private LocalDateTime modifyAt;
}