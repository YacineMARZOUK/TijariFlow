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

    @Override
    public ProductResponseDto findProductById(Long id){
        Product product = productRepository.findByEstSupprimeFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("Produit non trouvé ou supprimé avec ID: " + id));
        return productMapper.toResponseDto(product);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productDto) {
        Product product = productRepository.findByIdAndEstSupprimeFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé ou supprimé avec ID: " + id));

        // Utilise MapStruct pour mettre à jour les champs
        productMapper.updateEntityFromDto(productDto, product);
        product = productRepository.save(product);
        return productMapper.toResponseDto(product);
    }

    @Override
    public void softDeleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec ID: " + id));

        product.setEstSupprime(true);
        productRepository.save(product);
    }


}
