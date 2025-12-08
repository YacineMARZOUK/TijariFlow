package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class PaiementRequestDto {

    @NotNull(message = "Le montant du paiement est obligatoire.")
    @Positive(message = "Le montant doit être positif.")
    private BigDecimal amount;
}
