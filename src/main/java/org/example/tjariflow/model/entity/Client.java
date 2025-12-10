package org.example.tjariflow.model.entity;
import org.example.tjariflow.model.enums.CustomerTierStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Client extends User {
    @Column(name = "nom_complet", nullable = false)
    private String nomComplet;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_tier")
    @Builder.Default
    private CustomerTierStatus customerTier = CustomerTierStatus.BASIC;
    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}