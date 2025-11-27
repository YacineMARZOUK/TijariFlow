package dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;


@Data
public class ProductRequestDto {

    @NotBlank(message = "Le nom du produit est obligatoire.")
    private String nom;

    @NotNull(message = "Le prix unitaire HT est obligatoire.")
    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à zéro.")
    private BigDecimal prixUnitaireHT;

    @NotNull(message = "Le stock disponible est obligatoire.")
    @Min(value = 0, message = "Le stock ne peut pas être négatif.")
    private Integer stockDisponible;
}
