package service.impl;

import dto.*;
import entity.*;
import enums.OrderStatus;
import exception.BusinessRuleViolationException;
import exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.CommandeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ClientRepository;
import repository.CommandeRepository;
import repository.PaiementRepository;
import repository.ProductRepository;
import service.CommandeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate; // Changement : Importe LocalDate pour le paiement
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final PaiementRepository paiementRepository;
    private final CommandeMapper commandeMapper;

    private static final BigDecimal TVA_RATE = new BigDecimal("0.20"); // 20%

    @Override
    @Transactional
    public CommandeResponseDto createCommande(CommandeCreationRequestDto requestDto) {

        Client client = clientRepository.findById(requestDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé."));

        Commande commande = Commande.builder().client(client).build();

        List<LigneCommande> lignes = new ArrayList<>();
        BigDecimal sousTotalHT = BigDecimal.ZERO;

        for (LigneCommandeRequestDto ligneDto : requestDto.getLignes()) {
            Product product = productRepository.findById(ligneDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé."));

            BigDecimal totalLigneHT = product.getPrixUnitaireHT().multiply(new BigDecimal(ligneDto.getQuantite()));

            LigneCommande ligne = LigneCommande.builder()
                    .commande(commande)
                    .product(product)
                    .quantite(ligneDto.getQuantite())
                    .prixUnitaireAuMomentDeLaCommande(product.getPrixUnitaireHT())
                    .totalLigneHT(totalLigneHT)
                    .build();

            lignes.add(ligne);
            sousTotalHT = sousTotalHT.add(totalLigneHT);
        }

        commande.setLignesCommande(lignes);
        commande.setSousTotalHT(sousTotalHT.setScale(2, RoundingMode.HALF_UP));
        commande.setMontantHTApresRemise(sousTotalHT.setScale(2, RoundingMode.HALF_UP));
        commande.setRemiseFidelite(BigDecimal.ZERO);
        commande.setRemisePromo(BigDecimal.ZERO);

        BigDecimal montantTVA = sousTotalHT.multiply(TVA_RATE);
        commande.setMontantTVA(montantTVA.setScale(2, RoundingMode.HALF_UP));

        BigDecimal totalTTC = sousTotalHT.add(montantTVA);
        commande.setTotalTTC(totalTTC.setScale(2, RoundingMode.HALF_UP));
        commande.setMontantRestant(totalTTC.setScale(2, RoundingMode.HALF_UP));

        commande = commandeRepository.save(commande);

        return commandeMapper.toResponseDto(commande);
    }

    @Override
    public CommandeResponseDto getCommandeById(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec ID: " + id));
        return commandeMapper.toResponseDto(commande);
    }

    @Override
    public List<CommandeResponseDto> getCommandesByClientId(Long clientId) {
        List<Commande> commandes = commandeRepository.findByClientId(clientId);
        return commandes.stream()
                .map(commandeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommandeResponseDto updateCommandeStatus(Long id, OrderStatus newStatus) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec ID: " + id));

        if (commande.getStatut() == OrderStatus.CANCELED && newStatus != OrderStatus.CANCELED) {
            throw new BusinessRuleViolationException("Impossible de réactiver une commande annulée.");
        }

        if (newStatus == OrderStatus.CONFIRMED && commande.getMontantRestant().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuleViolationException("Impossible de marquer comme CONFIRMÉE (Payée). Le montant restant est de : " + commande.getMontantRestant());
        }

        commande.setStatut(newStatus);
        commande = commandeRepository.save(commande);

        return commandeMapper.toResponseDto(commande);
    }

    // --- NOUVEAU: Traitement d'un Paiement ---
    @Override
    @Transactional
    public CommandeResponseDto processPayment(Long id, PaiementRequestDto paiementDto) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec ID: " + id));

        BigDecimal montantPaiement = paiementDto.getAmount();

        if (montantPaiement.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleViolationException("Le montant du paiement doit être positif.");
        }

        BigDecimal montantRestant = commande.getMontantRestant();

        if (montantRestant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleViolationException("Cette commande est déjà entièrement payée.");
        }


        Paiement paiement = Paiement.builder()
                .commande(commande)
                .datePaiement(LocalDate.now())
                .montant(montantPaiement)
                .build();

        paiementRepository.save(paiement);

        //Misejour Commande
        BigDecimal nouveauMontantRestant = montantRestant.subtract(montantPaiement);

        if (nouveauMontantRestant.compareTo(BigDecimal.ZERO) < 0) {
            commande.setMontantRestant(BigDecimal.ZERO);
            commande.setStatut(OrderStatus.CONFIRMED);

        } else if (nouveauMontantRestant.compareTo(BigDecimal.ZERO) == 0) {
            commande.setMontantRestant(BigDecimal.ZERO);
            commande.setStatut(OrderStatus.CONFIRMED);

        } else {
            commande.setMontantRestant(nouveauMontantRestant.setScale(2, RoundingMode.HALF_UP));
        }

        if (commande.getPaiements() == null) {
            commande.setPaiements(new ArrayList<>());
        }
        commande.getPaiements().add(paiement);

        commande = commandeRepository.save(commande);

        return commandeMapper.toResponseDto(commande);
    }
}