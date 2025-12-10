package org.example.tjariflow.dto.response.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ClientOrderStatsResponseBasicDTO {
    private long totalOrders;
    private BigDecimal totalSpentConfirmed;
    @JsonFormat(pattern = "dd MMMM yyyy ", locale = "fr")
    private LocalDate firstOrderDate;
    @JsonFormat(pattern = "dd MMMM yyyy ", locale = "fr")
    private LocalDate lastOrderDate;
    private List<OrderHistoryBasicDTO> orders;
}
