package org.example.tjariflow.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    @NotNull(message = "Client  cannot be null")
    private String clientId;
    private List<OrderItemRequestDTO> orderItems;
    @Pattern(regexp = "PROMO-[A-Z0-9]{4}" , message = "Invalid promo code format")
    private String codePromo;

}
