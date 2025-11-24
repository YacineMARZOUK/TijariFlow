package entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;


    @Column(precision = 10, scale = 2)
    private BigDecimal prixUnitaireHT;

    private Integer stockDisponible;


    private boolean estSupprime = false;
}