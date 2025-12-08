package service;

import dto.CommandeCreationRequestDto;
import dto.CommandeResponseDto;
import dto.PaiementRequestDto;
import enums.OrderStatus;

import java.util.List;

public interface CommandeService {

    CommandeResponseDto createCommande(CommandeCreationRequestDto requestDto);

    CommandeResponseDto getCommandeById(Long id);
    List<CommandeResponseDto> getCommandesByClientId(Long clientId);

    CommandeResponseDto updateCommandeStatus(Long id, OrderStatus newStatus);


    CommandeResponseDto processPayment(Long id, PaiementRequestDto paiementDto);
}
