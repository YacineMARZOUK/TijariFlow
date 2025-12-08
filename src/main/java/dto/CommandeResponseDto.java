package dto;

import enums.OrderStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandeResponseDto {
    private Long id;
    private Long clientId;
    private LocalDateTime dateCommande;
    private OrderStatus statut;

    private List<LigneCommandeResponseDto> lignes;

    private BigDecimal sousTotalHT;
    private BigDecimal remiseFidelite;
    private BigDecimal remisePromo;
    private BigDecimal montantHTApresRemise;
    private BigDecimal montantTVA;
    private BigDecimal totalTTC;
    private BigDecimal montantRestant;
    private String codePromo;
}