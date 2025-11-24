package entity;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantite;

    // Prix fixé au moment de la commande (pour la traçabilité)
    @Column(precision = 10, scale = 2)
    private BigDecimal prixUnitaireAuMomentDeLaCommande;

    // Total Ligne HT (prix * quantité)
    @Column(precision = 10, scale = 2)
    private BigDecimal totalLigneHT;
}