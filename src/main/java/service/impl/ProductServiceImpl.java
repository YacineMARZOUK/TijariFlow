package service.impl;

import dto.ProductRequestDto;
import dto.ProductResponseDto;
import entity.Product;
import mapper.ProductMapper;
import repository.ProductRepository;
import service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct (ProductRequestDto productDto){
        Product product = productMapper.ToEntity(productDto);
        product.setEstSupprime(false);
        product = productRepository.save(product);
        return productMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> findAllActiveProducts(){
        return productRepository.findByEstSupprimeFalse().stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
