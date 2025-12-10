package org.example.tjariflow.service;


import org.example.tjariflow.dto.request.PaymentRequestDTO;
import org.example.tjariflow.dto.request.PaymentRequestStatusDTO;
import org.example.tjariflow.dto.response.detail.PaymentResponseDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    PaymentResponseDetailDTO createPayment(PaymentRequestDTO paymentRequestDTO);
    Page<PaymentResponseDetailDTO> getAllPayments(Pageable pageable);
    PaymentResponseDetailDTO getPaymentById(String id);
    PaymentResponseDetailDTO updateStatus(String id, PaymentRequestStatusDTO status);
}
