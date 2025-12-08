package dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class LigneCommandeRequestDto {

    @NotNull(message = "L'ID du produit est obligatoire.")
    private Long productId;

    @NotNull(message = "La quantité est obligatoire.")
    @Positive(message = "La quantité doit être positive.")
    private Integer quantite;
}