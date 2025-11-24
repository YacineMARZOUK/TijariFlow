package entity;

import enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    // Numéro séquentiel pour cette commande (1, 2, 3...)
    private Integer numeroSequentiel;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal montant;

    @Column(nullable = false)
    private String typePaiement; // ESPÈCES, CHÈQUE, VIREMENT

    @Enumerated(EnumType.STRING)
    private PaymentStatus statut = PaymentStatus.EN_ATTENTE;

    // Références et dates spécifiques
    private String reference; // REÇU-001, CHQ-7894561, VIR-2025...

    private String banque;

    private LocalDate datePaiement; // Date où le client a payé
    private LocalDate dateEncaissement; // Date d'encaissement effectif
    private LocalDate dateEcheance; // Pour Chèque/Virement différé
}