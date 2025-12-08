package service;

import dto.CommandeCreationRequestDto;
import dto.CommandeResponseDto;
import java.util.List;

public interface CommandeService {

    CommandeResponseDto createCommande(CommandeCreationRequestDto requestDto);

    CommandeResponseDto getCommandeById(Long id);
    List<CommandeResponseDto> getCommandesByClientId(Long clientId);

    // Méthodes de gestion de cycle de vie
    // CommandeResponseDto updateCommandeStatus(Long id, OrderStatus newStatus);
    // CommandeResponseDto processPayment(Long id, BigDecimal amount);
}
