package mapper;

import dto.ProductRequestDto;
import dto.ProductResponseDto;
import entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDto toResponseDto(Product product);

    Product ToEntity(ProductRequestDto dto);

    void updateEntityFromDto(ProductRequestDto dto, @MappingTarget Product product  );
}
