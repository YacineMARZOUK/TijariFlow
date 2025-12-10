package org.example.tjariflow.dto.response.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class ProductResponseDetailDTO {
    private String id;
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", locale = "fr")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", locale = "fr")
    private LocalDateTime modifyAt;
    private String productName;
    private BigDecimal unitPrice;
    private boolean deleted;
    private Integer quantity;

}

