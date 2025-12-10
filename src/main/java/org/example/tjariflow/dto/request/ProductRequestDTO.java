package org.example.tjariflow.dto.request;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductRequestDTO {
    @NotBlank(message = "Le nom du produit est requis.")
    private String productName;

    @NotNull(message = "Le prix unitaire est requis.")
    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à 0.")
    private BigDecimal unitPrice;
    @NotNull(message = "La quantité est requise.")
    @Positive(message = "La quantité doit être un nombre positif.")
    private Integer quantity;

}