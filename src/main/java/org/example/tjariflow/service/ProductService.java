package org.example.tjariflow.service;

 import org.example.tjariflow.dto.request.ProductRequestDTO;
 import org.example.tjariflow.dto.response.basic.ProductResponseBasicDTO;
 import org.example.tjariflow.dto.response.detail.ProductResponseDetailDTO;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponseDetailDTO createProduct(ProductRequestDTO productRequestDTO);
    Page<ProductResponseDetailDTO> getAllProductsDetail(Pageable pageable);
    Page<ProductResponseDetailDTO> getAllActiveProducts(Pageable pageable);
    ProductResponseDetailDTO updateProduct(String productId, ProductRequestDTO productRequestDTO);
    ProductResponseDetailDTO getProductById(String productId);
    void deleteProduct(String productId);
    Page<ProductResponseBasicDTO> getAllProducts(Pageable pageable);
}
