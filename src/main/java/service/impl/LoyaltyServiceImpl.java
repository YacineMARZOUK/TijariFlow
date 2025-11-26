package service.impl;

import entity.Client;
import enums.CustomerTier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ClientRepository;
import service.LoyaltyService;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void calculateAndSetTier(Client client){
        Integer currentTotalOrders = client.getNombreTotalCommandes();
        BigDecimal currentTotalAmount = client.getMontantTotalCumule();
        CustomerTier newTier = CustomerTier.BASIC;


        if(currentTotalOrders >= 20 || currentTotalAmount.compareTo(new BigDecimal("15000.00")) >= 0){
            newTier = CustomerTier.PLATINUM;
        }else if (currentTotalOrders >= 10 || currentTotalAmount.compareTo(new BigDecimal("5000.00")) >= 0) {
            newTier = CustomerTier.GOLD;

            // 3. SILVER: >= 3 commandes OU >= 1,000 DH
        } else if (currentTotalOrders >= 3 || currentTotalAmount.compareTo(new BigDecimal("1000.00")) >= 0) {
            newTier = CustomerTier.SILVER;
        }

        if(newTier != client.getNiveauFidelite()){
            client.setNiveauFidelite(newTier);
            clientRepository.save(client);
        }


    }

    @Override
    public BigDecimal getLoyaltyDiscountRate(CustomerTier tier) {
        // Retourne le taux (ex: 0.15) à partir des constantes définies
        return LoyaltyConstants.TIER_DISCOUNTS.getOrDefault(tier, BigDecimal.ZERO);
    }


}
