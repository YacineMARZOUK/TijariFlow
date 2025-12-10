package org.example.tjariflow.dto.response.detail;
import org.example.tjariflow.model.enums.CustomerTierStatus;
import org.example.tjariflow.model.enums.Roles;
import lombok.Data;

@Data
public class ClientResponseDetailDTO {
    private String id;
    private String userName;
    private String password;
    private String nomComplet;
    private String email;
    private String customerTier;
}
