package dto;

import enums.OrderStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class OrderStatusUpdateDto {
    @NotNull(message = "Le nouveau statut est obligatoire.")
    private OrderStatus newStatus;
}