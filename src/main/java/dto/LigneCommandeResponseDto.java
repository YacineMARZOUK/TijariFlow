package dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeResponseDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantite;
    private BigDecimal prixUnitaireAuMomentDeLaCommande;
    private BigDecimal totalLigneHT;
}