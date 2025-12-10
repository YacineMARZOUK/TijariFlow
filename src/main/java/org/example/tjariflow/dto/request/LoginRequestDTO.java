package org.example.tjariflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Le usernaeme d'utilisateur est requis.")
    private String username;
    @NotBlank(message = "Le password d'utilisateur est requis.")
    private String password;
}

