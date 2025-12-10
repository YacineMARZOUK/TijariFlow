package org.example.tjariflow.repository;

import org.example.tjariflow.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("SELECT MAX(p.numeroPayment) FROM Payment p WHERE p.order.id = :orderId")
    Integer findLastNumberByOrder(@Param("orderId") String orderId);

}
