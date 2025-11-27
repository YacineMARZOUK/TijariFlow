package service.impl;

import enums.CustomerTier;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ClientRepository;
import repository.CommandeRepository;
import repository.ProductRepository;
import service.LoyaltyService;
import service.OrderService;
import service.ProductService;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CommandeRepository commandeRepository;
    private final LoyaltyService loyaltyService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    private static final int SCALE = 2;

    private static final BigDecimal TAUX_TVA = new BigDecimal("0.20");

    // remise basees sur la commande actuelle
    private static final Map<CustomerTier, BigDecimal[]> DISCOUNT_RULES = Map.of(
            CustomerTier.PLATINUM, new BigDecimal[]{new BigDecimal("1200.00"), new BigDecimal("0.15")},
            CustomerTier.GOLD, new BigDecimal[]{new BigDecimal("800.00"), new BigDecimal("0.10")},
            CustomerTier.SILVER, new BigDecimal[]{new BigDecimal("500.00"), new BigDecimal("0.05")},
            CustomerTier.BASIC, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO}
    );

}
