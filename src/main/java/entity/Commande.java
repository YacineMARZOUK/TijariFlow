package entity;

import enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCommande = LocalDateTime.now();

    // Relations
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> lignesCommande;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> paiements;

    // Détails financiers (précision 10, scale 2 pour tous les montants)
    @Column(precision = 10, scale = 2)
    private BigDecimal sousTotalHT; // Avant toutes remises

    @Column(precision = 10, scale = 2)
    private BigDecimal remiseFidelite = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal remisePromo = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantHTApresRemise;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantTVA;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalTTC;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantRestant;

    private String codePromo;

    @Enumerated(EnumType.STRING)
    private OrderStatus statut = OrderStatus.PENDING;
}