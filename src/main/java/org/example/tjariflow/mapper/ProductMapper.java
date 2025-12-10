package org.example.tjariflow.mapper;

import org.example.tjariflow.dto.request.ProductRequestDTO;
import org.example.tjariflow.dto.response.basic.ProductResponseBasicDTO;
import org.example.tjariflow.dto.response.detail.ProductResponseDetailDTO;
import org.example.tjariflow.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target="id" , ignore = true)
    Product toEntity(ProductRequestDTO dto);
    ProductResponseDetailDTO toResponseDetail(Product entity);
    ProductResponseBasicDTO toResponseBasic(Product entity);

}
