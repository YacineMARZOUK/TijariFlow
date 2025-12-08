package service.impl;

import entity.Client;
import enums.CustomerTier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ClientRepository;
import service.LoyaltyService;

import java.math.BigDecimal;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {

    private final ClientRepository clientRepository;

    // taux de remise
    private static final Map<CustomerTier, BigDecimal> TIER_DISCOUNT_RATES = Map.of(
            CustomerTier.PLATINUM, new BigDecimal("0.15"),
            CustomerTier.GOLD, new BigDecimal("0.10"),
            CustomerTier.SILVER, new BigDecimal("0.05"),
            CustomerTier.BASIC, BigDecimal.ZERO
    );

    // Constantes de seuils cumulatifs pour la PROMOTION de niveau
    private static final Map<CustomerTier, BigDecimal[]> CUMULATIVE_THRESHOLDS = Map.of(
            CustomerTier.PLATINUM, new BigDecimal[]{new BigDecimal("20"), new BigDecimal("15000.00")},
            CustomerTier.GOLD, new BigDecimal[]{new BigDecimal("10"), new BigDecimal("5000.00")},
            CustomerTier.SILVER, new BigDecimal[]{new BigDecimal("3"), new BigDecimal("1000.00")},
            CustomerTier.BASIC, new BigDecimal[]{new BigDecimal("0"), new BigDecimal("0.00")}
    );

    @Override
    @Transactional
    public void calculateAndSetTier(Client client) {

        Integer currentTotalOrders = client.getNombreTotalCommandes();
        BigDecimal currentTotalAmount = client.getMontantTotalCumule();
        CustomerTier newTier = CustomerTier.BASIC;

        // verification des seuils
        // pour PLATINUM
        BigDecimal[] platThresholds = CUMULATIVE_THRESHOLDS.get(CustomerTier.PLATINUM);
        if (currentTotalOrders >= platThresholds[0].intValue() || currentTotalAmount.compareTo(platThresholds[1]) >= 0) {
            newTier = CustomerTier.PLATINUM;

            // pour GOLD
        } else {
            BigDecimal[] goldThresholds = CUMULATIVE_THRESHOLDS.get(CustomerTier.GOLD);
            if (currentTotalOrders >= goldThresholds[0].intValue() || currentTotalAmount.compareTo(goldThresholds[1]) >= 0) {
                newTier = CustomerTier.GOLD;

                //  pour SILVER
            } else {
                BigDecimal[] silverThresholds = CUMULATIVE_THRESHOLDS.get(CustomerTier.SILVER);
                if (currentTotalOrders >= silverThresholds[0].intValue() || currentTotalAmount.compareTo(silverThresholds[1]) >= 0) {
                    newTier = CustomerTier.SILVER;
                }
            }
        }

        // 2. Mise à jour de niveau
        if (newTier != client.getNiveauFidelite()) {
            client.setNiveauFidelite(newTier);
            clientRepository.save(client);
        }
    }

    @Override
    public BigDecimal getLoyaltyDiscountRate(CustomerTier tier) {
        // Cette méthode fournit uniquement le taux (utilisée par OrderService)
        return TIER_DISCOUNT_RATES.getOrDefault(tier, BigDecimal.ZERO);
    }
}


