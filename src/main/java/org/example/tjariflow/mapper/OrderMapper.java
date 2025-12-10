package org.example.tjariflow.mapper;


import org.example.tjariflow.dto.request.OrderRequestDTO;
import org.example.tjariflow.dto.response.basic.OrderResponseBasicAdminDTO;
import org.example.tjariflow.dto.response.detail.OrderResponseDetailDTO;
import org.example.tjariflow.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Order toEntity(OrderRequestDTO dto);

    OrderResponseDetailDTO toResponseDetail(Order entity);
    OrderResponseBasicAdminDTO toResponseBasicAdmin(Order entity);
}
