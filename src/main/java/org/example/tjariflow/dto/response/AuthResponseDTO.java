package org.example.tjariflow.dto.response;

import org.example.tjariflow.model.enums.Roles;
import lombok.Data;

@Data
public class AuthResponseDTO {
    private String message;
    private String id;
    private String useName;
    private Roles role;
}
