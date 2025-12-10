package org.example.tjariflow.dto.response.basic;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseBasicDTO {
    private ProductResponseBasicDTO product;
    private Integer quantity;
    private BigDecimal lineTotal;
}
