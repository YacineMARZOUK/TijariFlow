package org.example.tjariflow.model.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Product extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String productName;
    @Column(name="unit_price", nullable = false)
    private BigDecimal unitPrice;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Builder.Default
    @Column(name="deleted", nullable = false)
    private Boolean deleted = false;
}