package dto;

import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;


@Data
public class ProductRequestDto {

    @NotBlank(message = "le nom du produit est requis")
    private String nom;

    @DecimalMin(value="0.01", message = "Le prix doit etre positif")
    private BigDecimal prixUnitaireHT;

    @Min(value = 0, message = "le stock ne peut pas etre negatif ")
    private Integer stockDisponible;
}
