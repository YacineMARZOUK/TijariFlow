package org.example.tjariflow.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tjariflow.dto.request.ClientRequestDTO;
import org.example.tjariflow.dto.response.basic.ClientResponseBasicDTO;
import org.example.tjariflow.dto.response.detail.ClientResponseDetailDTO;
import org.example.tjariflow.exception.ConflictStateException;
import org.example.tjariflow.exception.InvalidCredentialsException;
import org.example.tjariflow.exception.ResourceNotFoundException;
import org.example.tjariflow.mapper.ClientMapper;
import org.example.tjariflow.model.entity.Client;
import org.example.tjariflow.repository.ClientRepository;
import org.example.tjariflow.repository.UserRepository;
import org.example.tjariflow.service.ClientService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientResponseDetailDTO createClient(ClientRequestDTO clientRequestDTO) {
        if (userRepository.findByUserName(clientRequestDTO.getUserName()).isPresent()) {
            throw new ConflictStateException("Username exists deja");
        }
        if(clientRepository.findByEmail(clientRequestDTO.getEmail()).isPresent()) {
            throw new ConflictStateException("Email exists deja");
        }
        if(!clientRequestDTO.getPassword().equals(clientRequestDTO.getConfirmPassword())) {
            throw new InvalidCredentialsException("Password and Confirm Password do not match");
        }
        String salt = BCrypt.gensalt();
        String hach = BCrypt.hashpw(clientRequestDTO.getPassword(), salt);
        clientRequestDTO.setPassword(hach);
        Client client = clientMapper.toEntity(clientRequestDTO);
        return clientMapper.toClientDetail(clientRepository.save(client));
    }

    @Override
    public Page<ClientResponseDetailDTO> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::toClientDetail);
    }

    @Override
    public ClientResponseDetailDTO getClientDetailById(String id) {
        return clientRepository.findById(id)
                .map(clientMapper::toClientDetail)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    @Override
    public ClientResponseBasicDTO getClientBasicByid(String id) {
        return clientRepository.findById(id)
                .map(clientMapper::toClientBasic)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    }

    @Override

    public ClientResponseDetailDTO updateClient(String id, ClientRequestDTO clientRequestDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        if(!clientRequestDTO.getPassword().equals(clientRequestDTO.getConfirmPassword())) {
            throw new InvalidCredentialsException("Password and Confirm Password do not match");
        }
        String salt = BCrypt.gensalt();
        String hach = BCrypt.hashpw(clientRequestDTO.getPassword(), salt);
        clientRequestDTO.setPassword(hach);
        existingClient.setUserName(clientRequestDTO.getUserName());
        existingClient.setNomComplet(clientRequestDTO.getNom()+" "+clientRequestDTO.getPrenom());
        existingClient.setEmail(clientRequestDTO.getEmail());
        existingClient.setPassword(hach);
        return clientMapper.toClientDetail(clientRepository.save(existingClient));

    }

    @Override
    public void deleteClient(String id) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        clientRepository.delete(existingClient);
    }
}
