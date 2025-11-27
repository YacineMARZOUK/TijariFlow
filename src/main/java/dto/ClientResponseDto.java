package dto;

import enums.CustomerTier;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ClientResponseDto {

    private Long id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;

    // Statistiques
    private Integer nombreTotalCommandes;
    private BigDecimal montantTotalCumule;
    private LocalDate datePremiereCommande;
    private LocalDate dateDerniereCommande;
    private CustomerTier niveauFidelite;

    // Informations utilisateur
    private Long userId;
    private String username;
}
