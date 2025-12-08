package controller;

import dto.CommandeCreationRequestDto;
import dto.CommandeResponseDto;
import dto.OrderStatusUpdateDto;
import dto.PaiementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CommandeService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public ResponseEntity<CommandeResponseDto> createCommande(@Valid @RequestBody CommandeCreationRequestDto requestDto) {
        CommandeResponseDto createdCommande = commandeService.createCommande(requestDto);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<CommandeResponseDto> getCommandeById(@PathVariable Long id) {
        CommandeResponseDto commande = commandeService.getCommandeById(id);
        return ResponseEntity.ok(commande);
    }

    //commandes pour un client
    @GetMapping("/admin/client/{clientId}")
    public ResponseEntity<List<CommandeResponseDto>> getCommandesByClientId(@PathVariable Long clientId) {
        List<CommandeResponseDto> commandes = commandeService.getCommandesByClientId(clientId);
        return ResponseEntity.ok(commandes);
    }



    @PatchMapping("/admin/{id}/status")
    public ResponseEntity<CommandeResponseDto> updateCommandeStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateDto statusDto) {
        CommandeResponseDto updatedCommande = commandeService.updateCommandeStatus(id, statusDto.getNewStatus());
        return ResponseEntity.ok(updatedCommande);
    }

    @PostMapping("/admin/{id}/paiement")
    public ResponseEntity<CommandeResponseDto> processPayment(
            @PathVariable Long id,
            @Valid @RequestBody PaiementRequestDto paiementDto) {

        // miseejour le montant restant
        CommandeResponseDto updatedCommande = commandeService.processPayment(id, paiementDto);
        return ResponseEntity.ok(updatedCommande);
    }
}