package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientCreationRequestDto {

    @NotBlank(message = "nom doit pas etre vide ")
    private String nom;

    @NotBlank(message = "l email est obligatoire")
    @Email(message = "Format d email invalide ")
    private String email;

    private String telephone;
    private String adresse;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire.")
    private String username;

    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères.")
    private String password;

}
