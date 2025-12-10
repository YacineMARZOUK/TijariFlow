package org.example.tjariflow.controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tjariflow.dto.request.OrderRequestDTO;
import org.example.tjariflow.dto.response.basic.OrderResponseBasicAdminDTO;
import org.example.tjariflow.dto.response.detail.OrderResponseDetailDTO;
import org.example.tjariflow.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class GestionCammandesController {
    private final OrderService orderService;
    @PostMapping("/admin/create")
    public ResponseEntity<OrderResponseDetailDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDetailDTO response = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body("Order deleted successfully");
    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<OrderResponseDetailDTO> getOrderById(@PathVariable String id) {
        OrderResponseDetailDTO response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/all")
    public ResponseEntity<Page<OrderResponseBasicAdminDTO>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size,
                                                                         @RequestParam(defaultValue = "createAt") String sortBy,
                                                                         @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size, sort);
        Page<OrderResponseBasicAdminDTO> orderPage = orderService.getAllOrdersAdmin(pageable);
        return ResponseEntity.ok().body(orderPage);
    }
    @PostMapping("/admin/{id}/confirm")
    public ResponseEntity<OrderResponseDetailDTO> confirmOrder(@PathVariable String id) {
        OrderResponseDetailDTO oo =  orderService.confirmedOrder(id);
        return ResponseEntity.ok(oo);
    }


}