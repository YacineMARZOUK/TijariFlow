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

    private Integer numeroSequentiel;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal montant;

    @Column(nullable = false)
    private String typePaiement;

    @Enumerated(EnumType.STRING)
    private PaymentStatus statut = PaymentStatus.EN_ATTENTE;

    private String reference;

    private String banque;

    private LocalDate datePaiement;
    private LocalDate dateEncaissement;
    private LocalDate dateEcheance;
}