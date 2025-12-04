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
    private final HttpSession httpSession; // Pour récupérer l'utilisateur connecté

    // Clé utilisée pour stocker l'ID de l'utilisateur connecté (à synchroniser avec votre AuthController/Filter)
    public static final String SESSION_USER_KEY = "CURRENT_USER_ID";

    /**
     * Crée un nouveau client et l'utilisateur associé. Opération réservée à l'ADMIN.
     */
    @Override
    @Transactional
    public ClientResponseDto createClient(ClientCreationRequestDto clientDto) {
        // 1. Validation de l'unicité du username
        if (userRepository.findByUsername(clientDto.getUsername()).isPresent()) {
            throw new BusinessRuleViolationException("Nom d'utilisateur déjà pris.");
        }

        // 2. Création et enregistrement de l'utilisateur
        User user = clientMapper.toUserEntity(clientDto);
        user.setPassword(passwordEncoder.encode(clientDto.getPassword())); // Hachage
        // Le rôle est déjà défini à CLIENT dans le Mapper, mais on le garantit ici
        user.setRole(UserRole.CLIENT);
        user = userRepository.save(user);

        // 3. Création et enregistrement du client
        Client client = clientMapper.toEntity(clientDto);
        client.setUser(user); // Liaison OneToOne
        // Les autres champs (niveauFidelite, statistiques) sont initialisés via le constructeur @Builder par défaut
        client = clientRepository.save(client);

        // Mise à jour de la relation inverse (bidirectionnelle)
        user.setClient(client);
        userRepository.save(user);

        return clientMapper.toResponseDto(client);
    }

    /**
     * Retourne la liste de tous les clients. Opération réservée à l'ADMIN.
     */
    @Override
    public List<ClientResponseDto> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toResponseDto(clients);
    }

    /**
     * Retourne un client spécifique par son ID. Opération réservée à l'ADMIN.
     */
    @Override
    public ClientResponseDto findClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec ID: " + id));
        return clientMapper.toResponseDto(client);
    }

    /**
     * Permet à un CLIENT de consulter son propre profil.
     * L'ID de l'utilisateur est extrait de la session HTTP.
     */
    @Override
    public ClientResponseDto findClientProfile() {
        // 1. Récupérer l'ID de l'utilisateur connecté depuis la session
        Long currentUserId = (Long) httpSession.getAttribute(SESSION_USER_KEY);
        if (currentUserId == null) {
            // Devrait être intercepté par l'AuthenticationFilter, mais sécurité par défaut
            throw new ResourceNotFoundException("Aucune session utilisateur active.");
        }

        // 2. Trouver l'utilisateur et son client associé
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur de session non trouvé."));

        // Vérifier si cet utilisateur est un CLIENT et a une entité Client associée
        if (user.getRole() != UserRole.CLIENT || user.getClient() == null) {
            throw new BusinessRuleViolationException("Cet utilisateur n'est pas un client enregistré.");
        }

        // 3. Retourner les données du client
        return clientMapper.toResponseDto(user.getClient());
    }


}
