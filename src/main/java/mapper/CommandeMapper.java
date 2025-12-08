package mapper;

import dto.CommandeResponseDto;
import dto.LigneCommandeResponseDto;
import entity.Commande;
import entity.LigneCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommandeMapper {


    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.nom", target = "productName")
    LigneCommandeResponseDto toLigneResponseDto(LigneCommande ligneCommande);
    List<LigneCommandeResponseDto> toLigneResponseDto(List<LigneCommande> lignesCommande);


    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "lignesCommande", target = "lignes")
    CommandeResponseDto toResponseDto(Commande commande);

    List<CommandeResponseDto> toResponseDto(List<Commande> commandes);
}