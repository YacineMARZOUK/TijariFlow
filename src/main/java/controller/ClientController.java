package controller;

import dto.ClientCreationRequestDto;
import dto.ClientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ClientService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;


    @PostMapping("/admin/clients")
    public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientCreationRequestDto clientDto) {
        ClientResponseDto createdClient = clientService.createClient(clientDto);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("/admin/clients")
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        List<ClientResponseDto> clients = clientService.findAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/admin/clients/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        ClientResponseDto client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }


    @GetMapping("/profile")
    public ResponseEntity<ClientResponseDto> getMyProfile() {

        ClientResponseDto profile = clientService.findClientProfile();
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/admin/clients/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id,
                                                          @Valid @RequestBody ClientCreationRequestDto clientDto) {
        ClientResponseDto updatedClient = clientService.updateClient(id, clientDto);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/admin/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}