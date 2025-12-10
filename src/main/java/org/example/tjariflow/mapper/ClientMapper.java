package org.example.tjariflow.mapper;



import org.example.tjariflow.dto.request.ClientRequestDTO;
import org.example.tjariflow.dto.response.basic.ClientResponseBasicDTO;
import org.example.tjariflow.dto.response.detail.ClientResponseDetailDTO;
import org.example.tjariflow.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nomComplet", expression = "java(dto.getNom() + \" \" + dto.getPrenom())")
    @Mapping(target = "role", expression = "java(org.example.tjariflow.model.enums.Roles.CLIENT)")
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDetailDTO toClientDetail(Client entity);

    ClientResponseBasicDTO toClientBasic(Client entity);
}
