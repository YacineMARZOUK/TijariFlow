package org.example.tjariflow.repository;

import org.example.tjariflow.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
    boolean existsByCodePromo(String codePromo);
}
