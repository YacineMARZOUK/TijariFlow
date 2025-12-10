package org.example.tjariflow.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.tjariflow.dto.request.ClientRequestDTO;
import org.example.tjariflow.dto.response.basic.ClientOrderStatsResponseBasicDTO;
import org.example.tjariflow.dto.response.basic.ClientResponseBasicDTO;
import org.example.tjariflow.dto.response.detail.ClientResponseDetailDTO;
import org.example.tjariflow.service.ClientService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class GestionClientsController {
    private final ClientService clientService;
    @PostMapping("/admin/register")
    public ResponseEntity<ClientResponseDetailDTO> register(@Valid @RequestBody ClientRequestDTO request) {
        ClientResponseDetailDTO response = clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/admin/all")
    public ResponseEntity<Page<ClientResponseDetailDTO>> getAllClients(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @RequestParam(defaultValue = "createAt") String sortBy,
                                                                       @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size, sort);
        Page<ClientResponseDetailDTO> clientPage = clientService.getAllClients(pageable);
        return ResponseEntity.ok().body(clientPage);
    }
    @GetMapping("/me")
    public ResponseEntity<ClientResponseBasicDTO> getCurrentClient(HttpSession session) {
        String id = (String) session.getAttribute("USER_ID");
        ClientResponseBasicDTO response = clientService.getClientBasicByid(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<ClientResponseDetailDTO> getClientById(@PathVariable String id) {
        ClientResponseDetailDTO response = clientService.getClientDetailById(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ClientResponseDetailDTO> updateClient(@PathVariable     String id,
                                                                @Valid @RequestBody ClientRequestDTO request) {
        ClientResponseDetailDTO response = clientService.updateClient(id, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable String id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().body("Client deleted successfully");
    }
    @GetMapping("/stats")
    public ClientOrderStatsResponseBasicDTO getClientOrderStats(HttpSession session) {
        String id = (String) session.getAttribute("USER_ID");
        return clientService.getClientOrderStats(id);
    }
}