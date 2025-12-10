package org.example.tjariflow.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tjariflow.dto.request.PaymentRequestDTO;
import org.example.tjariflow.dto.request.PaymentRequestStatusDTO;
import org.example.tjariflow.dto.response.detail.PaymentResponseDetailDTO;
import org.example.tjariflow.exception.InvalidCredentialsException;
import org.example.tjariflow.exception.ResourceNotFoundException;
import org.example.tjariflow.mapper.PaymentMapper;
import org.example.tjariflow.model.entity.Client;
import org.example.tjariflow.model.entity.Order;
import org.example.tjariflow.model.entity.Payment;
import org.example.tjariflow.model.entity.Product;
import org.example.tjariflow.model.enums.CustomerTierStatus;
import org.example.tjariflow.model.enums.OrderStatus;
import org.example.tjariflow.model.enums.PaymentStatus;
import org.example.tjariflow.model.enums.PaymentType;
import org.example.tjariflow.repository.ClientRepository;
import org.example.tjariflow.repository.OrderRepository;
import org.example.tjariflow.repository.PaymentRepository;
import org.example.tjariflow.repository.ProductRepository;
import org.example.tjariflow.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public synchronized  Integer getNextPaymentNumber(String orderId) {
        Integer last = paymentRepository.findLastNumberByOrder(orderId);
        return (last == null) ? 1 : last + 1;
    }
    private void autoConfirmOrderIfFullyPaid(Order order) {
        if (order.getRemainingAmount().compareTo(BigDecimal.ZERO) == 0) {
            order.setStatus(OrderStatus.CONFIRMED);
        }
    }
    public void applyOrderBusinessRules(Order order) {
        if (order.getRemainingAmount().compareTo(BigDecimal.ZERO) > 0) {
            return;
        }
        order.getOrderItems().forEach(item -> {
            Product p = item.getProduct();
            p.setQuantity(p.getQuantity() - item.getQuantity());
            productRepository.save(p);
        });
        Client client = order.getClient();
        long totalOrders = client.getOrders().stream()
                .filter(o -> o.getStatus() == OrderStatus.CONFIRMED)
                .count();
        System.out.println("Total confirmed orders for client " + client.getNomComplet() + ": " + totalOrders);

        BigDecimal totatSpent = client.getOrders().stream()
                .filter(o-> o.getStatus().equals(OrderStatus.CONFIRMED))
                .map(Order::getTotalTTC)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(totalOrders >= 20 || totatSpent.compareTo(new BigDecimal("15000")) >= 0) {
            client.setCustomerTier(CustomerTierStatus.PLATINUM);
        } else if(totalOrders >= 10 || totatSpent.compareTo(new BigDecimal("5000")) >= 0) {
            client.setCustomerTier(CustomerTierStatus.GOLD);
        } else if(totalOrders >= 3 || totatSpent.compareTo(new BigDecimal("1000")) >= 0) {
            client.setCustomerTier(CustomerTierStatus.SILVER);
        } else {
            client.setCustomerTier(CustomerTierStatus.BASIC);
        }

        clientRepository.save(client);

    }

    @Override
    @Transactional
    public PaymentResponseDetailDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
        Optional<Order> order = orderRepository.findById(paymentRequestDTO.getOrderId());
        if (order.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }
        BigDecimal amount = paymentRequestDTO.getAmount();
        if(amount.compareTo(order.get().getRemainingAmount()) > 0) {
            throw new InvalidCredentialsException("Amount exceeds the remaining balance.");
        }
        if(paymentRequestDTO.getType() == PaymentType.ESPECES && amount.compareTo(new BigDecimal("20000")) > 0) {
            throw new InvalidCredentialsException("Cash amount exceeds 20,000 DH.");
        }
        Payment payment = new Payment();
        payment.setOrder(order.get());
        payment.setAmount(amount);
        payment.setType(paymentRequestDTO.getType());
        payment.setNumeroPayment(getNextPaymentNumber(order.get().getId()));
        switch(payment.getType()) {
            case ESPECES:
                payment.setStatus(PaymentStatus.CLEARED);
                payment.setDateDeposit(LocalDate.now());
                break;
            case CHEQUE:
            case VIREMENT:
                payment.setStatus(PaymentStatus.PENDING);
                payment.setDateDeposit(null);
                break;
            default:
                throw new InvalidCredentialsException("Invalid payment type");
        }
        order.get().setRemainingAmount(order.get().getRemainingAmount().subtract(amount));
        autoConfirmOrderIfFullyPaid(order.get());
        orderRepository.save(order.get());
        applyOrderBusinessRules(order.get());
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    @Transactional
    public Page<PaymentResponseDetailDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(paymentMapper::toResponse);
    }

    @Override
    @Transactional
    public PaymentResponseDetailDTO getPaymentById(String id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @Override
    @Transactional
    public PaymentResponseDetailDTO updateStatus(String id, PaymentRequestStatusDTO status) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) {
            throw new ResourceNotFoundException("Payment not found with id: " + id);
        }

        if (payment.get().getStatus() == PaymentStatus.CLEARED || payment.get().getStatus() == PaymentStatus.CANCELED) {
            throw new InvalidCredentialsException("Cannot update status of a cleared or canceled payment");
        }
        Order order = payment.get().getOrder();
        if (status.getPaymentStatus() == PaymentStatus.REJECTED || status.getPaymentStatus() == PaymentStatus.CANCELED) {
            order.setRemainingAmount(order.getRemainingAmount().add(payment.get().getAmount()));
            order.setStatus(OrderStatus.CANCELLED);
            autoConfirmOrderIfFullyPaid(order);
            orderRepository.save(order);
            applyOrderBusinessRules(order);
        }
        payment.get().setStatus(status.getPaymentStatus());
        if (status.getPaymentStatus() == PaymentStatus.CLEARED) {
            payment.get().setDateDeposit(LocalDate.now());
        }
        return paymentMapper.toResponse(paymentRepository.save(payment.get()));
    }

}