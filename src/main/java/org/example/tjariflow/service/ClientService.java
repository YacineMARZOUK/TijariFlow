package org.example.tjariflow.service;
import org.example.tjariflow.dto.request.ClientRequestDTO;

import org.example.tjariflow.dto.response.basic.ClientResponseBasicDTO;
import org.example.tjariflow.dto.response.detail.ClientResponseDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ClientService {
    ClientResponseDetailDTO createClient(ClientRequestDTO clientRequestDTO);
    Page<ClientResponseDetailDTO> getAllClients(Pageable pageable);
    ClientResponseDetailDTO getClientDetailById(String id);
    ClientResponseBasicDTO getClientBasicByid(String id);
    ClientResponseDetailDTO updateClient(String id, ClientRequestDTO clientRequestDTO);
    void deleteClient(String id);
}
