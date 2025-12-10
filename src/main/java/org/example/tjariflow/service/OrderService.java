package org.example.tjariflow.service;

import org.example.tjariflow.dto.request.OrderRequestDTO;
import org.example.tjariflow.dto.response.basic.OrderResponseBasicAdminDTO;
import org.example.tjariflow.dto.response.detail.OrderResponseDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDetailDTO createOrder(OrderRequestDTO orderRequestDTO);
    void deleteOrder(String orderId);
    OrderResponseDetailDTO getOrderById(String orderId);
    Page<OrderResponseBasicAdminDTO> getAllOrdersAdmin(Pageable pageable);
    OrderResponseDetailDTO confirmedOrder(String orderId);
}

