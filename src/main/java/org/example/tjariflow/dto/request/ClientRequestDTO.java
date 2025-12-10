package org.example.tjariflow.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequestDTO {

    @NotBlank(message = "Le nom est requis.")
    @Size(min = 3, max = 50, message = "Le nom complet doit contenir entre 3 et 50 caractères.")
    private String nom;

    @NotBlank(message = "Le prénom est requis.")
    @Size(min = 3, max = 50, message = "Le prénom complet doit contenir entre 3 et 50 caractères.")
    private String prenom;

    @NotBlank(message = "Le nom d'utilisateur est requis.")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit contenir entre 3 et 20 caractères.")
    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z0-9]{2,19}$",
            message = "Le nom d'utilisateur doit commencer par une lettre et contenir uniquement des lettres et des chiffres."
    )
    private String userName;

    @NotBlank(message = "Le mot de passe est requis.")
    @Size(min = 8, max = 64, message = "Le mot de passe doit contenir entre 8 et 64 caractères.")
    private String password;

    @NotBlank(message = "La confirmation du mot de passe est requise.")
    private String confirmPassword;

    @NotBlank(message = "L'email est requis.")
    @Email(message = "L'email doit être valide.")
    private String email;
}