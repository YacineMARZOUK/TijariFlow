package org.example.tjariflow.mapper;



import org.example.tjariflow.dto.request.PaymentRequestDTO;
import org.example.tjariflow.dto.response.detail.PaymentResponseDetailDTO;
import org.example.tjariflow.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    Payment toEntity(PaymentRequestDTO payment);

    PaymentResponseDetailDTO toResponse(Payment payment);
}

