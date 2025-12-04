package service.impl;

import dto.ClientCreationRequestDto;
import dto.ClientResponseDto;
import entity.Client;
import entity.User;
import enums.UserRole;
import exception.BusinessRuleViolationException;
import exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mapper.ClientMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.ClientRepository;
import repository.UserRepository;
import service.ClientService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    public static final String SESSION_USER_KEY = "CURRENT_USER_ID";


    @Override
    @Transactional
    public ClientResponseDto createClient(ClientCreationRequestDto clientDto) {

        if (userRepository.findByUsername(clientDto.getUsername()).isPresent()) {
            throw new BusinessRuleViolationException("Nom d'utilisateur déjà pris.");
        }


        User user = clientMapper.toUserEntity(clientDto);
        user.setPassword(passwordEncoder.encode(clientDto.getPassword())); // Hachage

        user.setRole(UserRole.CLIENT);
        user = userRepository.save(user);


        Client client = clientMapper.toEntity(clientDto);
        client.setUser(user);

        client = clientRepository.save(client);


        user.setClient(client);
        userRepository.save(user);

        return clientMapper.toResponseDto(client);
    }

    @Override
    public List<ClientResponseDto> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toResponseDto(clients);
    }

    @Override
    public ClientResponseDto findClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec ID: " + id));
        return clientMapper.toResponseDto(client);
    }


    @Override
    public ClientResponseDto findClientProfile() {

        Long currentUserId = (Long) httpSession.getAttribute(SESSION_USER_KEY);
        if (currentUserId == null) {

            throw new ResourceNotFoundException("Aucune session utilisateur active.");
        }


        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur de session non trouvé."));


        if (user.getRole() != UserRole.CLIENT || user.getClient() == null) {
            throw new BusinessRuleViolationException("Cet utilisateur n'est pas un client enregistré.");
        }


        return clientMapper.toResponseDto(user.getClient());
    }


}
