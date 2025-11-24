
package service;

import dto.ProductRequestDto;
import dto.ProductResponseDto;
import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productDto);
    List<ProductResponseDto> findAllActiveProducts();
    ProductResponseDto findProductById(Long id);
    ProductResponseDto updateProduct(Long id, ProductRequestDto productDto);
    void softDeleteProduct(Long id);
}