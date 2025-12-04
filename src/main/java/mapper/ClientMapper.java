package mapper;

import dto.ClientCreationRequestDto;
import dto.ClientResponseDto;
import entity.Client;
import entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {


    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    ClientResponseDto toResponseDto(Client client);

    List<ClientResponseDto> toResponseDto(List<Client> clients);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true) // L'utilisateur est créé séparément
    @Mapping(target = "nombreTotalCommandes", ignore = true)
    @Mapping(target = "montantTotalCumule", ignore = true)
    @Mapping(target = "datePremiereCommande", ignore = true)
    @Mapping(target = "dateDerniereCommande", ignore = true)
    @Mapping(target = "niveauFidelite", ignore = true)
    Client toEntity(ClientCreationRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "CLIENT")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    User toUserEntity(ClientCreationRequestDto dto);
}