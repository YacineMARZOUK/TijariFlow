package org.example.tjariflow.service.impl;

import org.example.tjariflow.dto.request.OrderItemRequestDTO;
import org.example.tjariflow.dto.request.OrderRequestDTO;
import org.example.tjariflow.dto.response.basic.OrderResponseBasicAdminDTO;
import org.example.tjariflow.dto.response.detail.OrderResponseDetailDTO;
import org.example.tjariflow.exception.ConflictStateException;
import org.example.tjariflow.exception.InvalidCredentialsException;
import org.example.tjariflow.exception.ResourceNotFoundException;
import org.example.tjariflow.mapper.OrderMapper;
import org.example.tjariflow.model.entity.Client;
import org.example.tjariflow.model.entity.Order;
import org.example.tjariflow.model.entity.OrderItem;
import org.example.tjariflow.model.entity.Product;
import org.example.tjariflow.model.enums.CustomerTierStatus;
import org.example.tjariflow.model.enums.OrderStatus;
import org.example.tjariflow.repository.ClientRepository;
import org.example.tjariflow.repository.OrderRepository;
import org.example.tjariflow.repository.ProductRepository;
import org.example.tjariflow.service.OrderService;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public  class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDetailDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Optional<Client> client = clientRepository.findById(orderRequestDTO.getClientId());
        if (client.isEmpty()) {
            throw new ResourceNotFoundException("Client not found with ID: " + orderRequestDTO.getClientId());
        }
        Order order = Order.builder()
                .client(client.get())
                .subTotal(BigDecimal.ZERO)
                .discount(BigDecimal.ZERO)
                .TVA(BigDecimal.ZERO)
                .totalTTC(BigDecimal.ZERO)
                .remainingAmount(BigDecimal.ZERO)
                .build();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemRequestDTO itemRequestDTO : orderRequestDTO.getOrderItems()) {

            Optional<Product> product = productRepository.findByIdAndDeletedFalse(itemRequestDTO.getProductId());
            if (product.isEmpty()) {
                throw new ResourceNotFoundException("Product not found with ID: " + itemRequestDTO.getProductId());
            }
            if (product.get().getQuantity() < itemRequestDTO.getQuantity()) {
                order.setStatus(OrderStatus.REJECTED);
            }

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product.get())
                    .quantity(itemRequestDTO.getQuantity())
                    .unitPrice(product.get().getUnitPrice())
                    .lineTotal(product.get().getUnitPrice().multiply(BigDecimal.valueOf(itemRequestDTO.getQuantity())))
                    .build();
            orderItemList.add(item);
        }
        order.setOrderItems(orderItemList);

        BigDecimal subTotal = orderItemList.stream()
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discount = BigDecimal.ZERO;
        if(client.get().getCustomerTier().equals(CustomerTierStatus.SILVER)) {
            discount = subTotal.multiply(BigDecimal.valueOf(0.05));
        } else if (client.get().getCustomerTier().equals(CustomerTierStatus.GOLD)) {
            discount = subTotal.multiply(BigDecimal.valueOf(0.1));
        } else if (client.get().getCustomerTier().equals(CustomerTierStatus.PLATINUM)) {
            discount = subTotal.multiply(BigDecimal.valueOf(0.15));
        }
        if(orderRequestDTO.getCodePromo() != null){
            boolean exists = orderRepository.existsByCodePromo(orderRequestDTO.getCodePromo());
            if (exists) {
                throw new ConflictStateException("Promo code already used in another order .");
            }
            discount = discount.add(subTotal.multiply(BigDecimal.valueOf(0.05)));
        }
        order.setDiscount(discount);
        order.setCodePromo(orderRequestDTO.getCodePromo());
        order.setSubTotal(subTotal);
        order.setTVA(subTotal.subtract(discount).multiply(BigDecimal.valueOf(0.2)));
        order.setTotalTTC(order.getSubTotal().subtract(order.getDiscount()).add(order.getTVA()));
        order.setRemainingAmount(order.getTotalTTC());

        return orderMapper.toResponseDetail( orderRepository.save(order));
    }
    @Override
    @Transactional
    public void deleteOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepository.delete(order.get());
    }

    @Override
    @Transactional
    public OrderResponseDetailDTO getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toResponseDetail)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
    }

    @Override
    @Transactional
    public Page<OrderResponseBasicAdminDTO> getAllOrdersAdmin(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toResponseBasicAdmin);
    }

    @Override
    public OrderResponseDetailDTO confirmedOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        if (order.get().getRemainingAmount().compareTo(BigDecimal.ZERO) == 0) {
            order.get().setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order.get());
        } else {
            throw new InvalidCredentialsException("Cannot confirm order with remaining amount greater than zero.");
        }
        return orderMapper.toResponseDetail(order.get());

    }
}

