package org.example.tjariflow.repository;

import org.example.tjariflow.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,String> {
}
