package org.example.tjariflow.mapper;

import org.example.tjariflow.dto.request.OrderItemRequestDTO;
import org.example.tjariflow.model.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemRequestDTO dto);
}
