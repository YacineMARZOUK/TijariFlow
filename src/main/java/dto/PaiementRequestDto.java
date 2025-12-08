package dto;

import enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaiementRequestDto {

    @NotNull(message = "Le montant du paiement est obligatoire.")
    @DecimalMin(value = "0.01", message = "Le montant doit être positif.")
    private BigDecimal montant;

    @NotBlank(message = "Le type de paiement est obligatoire (ESPÈCES, CHÈQUE, VIREMENT).")
    private String typePaiement;

    @NotBlank(message = "La référence du paiement est obligatoire.")
    private String reference;

    private String banque;
    private LocalDate dateEcheance;

    private PaymentStatus statutInitial;
}