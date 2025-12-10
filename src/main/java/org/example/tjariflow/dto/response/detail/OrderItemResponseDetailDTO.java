package org.example.tjariflow.dto.response.detail;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItemResponseDetailDTO {
    private ProductResponseDetailDTO product;
    private Integer quantity;
    private BigDecimal lineTotal;
}

