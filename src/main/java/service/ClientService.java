package service;

import dto.ClientCreationRequestDto;
import dto.ClientResponseDto;

import java.util.List;

public interface ClientService {
    ClientResponseDto createClient(ClientCreationRequestDto clientDto);
    List<ClientResponseDto> findAllClients();
    ClientResponseDto findClientById(Long id); // Utilisé par l'ADMIN
    ClientResponseDto findClientProfile();
    ClientResponseDto updateClient(Long id, ClientCreationRequestDto clientDto);
    void deleteClient(Long id);
}
