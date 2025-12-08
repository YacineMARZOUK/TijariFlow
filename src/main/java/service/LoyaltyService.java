package service;

import entity.Client;
import enums.CustomerTier;

import java.math.BigDecimal;

public interface LoyaltyService {
    void calculateAndSetTier(Client client);
    BigDecimal getLoyaltyDiscountRate(CustomerTier tier);
}
