package entity;

import enums.CustomerTier;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String email;
    private String telephone;
    private String adresse;


    private Integer nombreTotalCommandes = 0;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantTotalCumule = BigDecimal.ZERO;

    private LocalDate datePremiereCommande;
    private LocalDate dateDerniereCommande;

    @Enumerated(EnumType.STRING)
    private CustomerTier niveauFidelite = CustomerTier.BASIC;


    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;


    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;
}
