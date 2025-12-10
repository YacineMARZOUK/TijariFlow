package org.example.tjariflow.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tjariflow.dto.request.PaymentRequestDTO;
import org.example.tjariflow.dto.request.PaymentRequestStatusDTO;
import org.example.tjariflow.dto.response.detail.PaymentResponseDetailDTO;
import org.example.tjariflow.exception.InvalidCredentialsException;
import org.example.tjariflow.model.enums.PaymentStatus;
import org.example.tjariflow.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@Controller
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class GestionPaymentsController {
    private final PaymentService paymentService;

    @PostMapping("/admin/create")
    public ResponseEntity<PaymentResponseDetailDTO> ceratePayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDetailDTO response = paymentService.createPayment(paymentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/admin/update/{id}/status")
    public ResponseEntity<PaymentResponseDetailDTO> updatePaymentStatus(@PathVariable String id, @Valid @RequestBody PaymentRequestStatusDTO status) {
        if(!EnumSet.allOf(PaymentStatus.class).contains(status.getPaymentStatus())) {
            throw new InvalidCredentialsException("Invalid payment status: " + status.getPaymentStatus());
        }
        PaymentResponseDetailDTO response = paymentService.updateStatus(id,status);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<PaymentResponseDetailDTO> getPaymentById(@PathVariable String id) {
        PaymentResponseDetailDTO response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/all")
    public ResponseEntity<Page<PaymentResponseDetailDTO>> getAllPayments(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size,
                                                                         @RequestParam(defaultValue = "createAt") String sortBy,
                                                                         @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size, sort);
        Page<PaymentResponseDetailDTO> paymentPage = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok().body(paymentPage);
    }

}
