package dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
public class CommandeCreationRequestDto {

    @NotNull(message = "L'ID du client est obligatoire.")
    private Long clientId;

    @Size(min = 1, message = "La commande doit contenir au moins une ligne.")
    private List<LigneCommandeRequestDto> lignes;


    private String codePromo;
}