package org.example.tjariflow.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestDTO {
    @NotNull(message = "add product u want to order")
    private String productId;
    @NotNull(message = "add quantity of the product")
    private Integer quantity;
}

