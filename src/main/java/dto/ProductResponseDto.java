package dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long id;
    private String nom;
    private BigDecimal prixUnitaireHT;
    private Integer stockDisponible;
    private boolean estSupprime;

}
