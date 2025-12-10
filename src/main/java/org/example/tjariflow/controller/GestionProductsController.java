package org.example.tjariflow.controller;

import org.example.tjariflow.dto.request.ProductRequestDTO;
import org.example.tjariflow.dto.response.basic.ProductResponseBasicDTO;
import org.example.tjariflow.dto.response.detail.ProductResponseDetailDTO;
import org.example.tjariflow.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class GestionProductsController {
    private final ProductService productService;
    @PostMapping("/admin/create")
    public ResponseEntity<ProductResponseDetailDTO> createProduct(@Valid @RequestBody ProductRequestDTO request) {
        ProductResponseDetailDTO responseDetailDTO = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDetailDTO);
    }
    @PutMapping("/admin/update/{productId}")
    public ResponseEntity<ProductResponseDetailDTO> updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductRequestDTO request) {
        ProductResponseDetailDTO responseDetailDTO = productService.updateProduct(productId, request);
        return ResponseEntity.ok(responseDetailDTO);
    }
    @GetMapping("/admin/all")
    public ResponseEntity<Page<ProductResponseDetailDTO>> getAllProductAdmin(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam(defaultValue = "createAt") String sortBy,
                                                                             @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size, sort);
        Page<ProductResponseDetailDTO> productResponses = productService.getAllProductsDetail(pageable);
        return ResponseEntity.ok().body(productResponses);
    }
    @GetMapping
    public ResponseEntity<Page<ProductResponseBasicDTO>> getAllProductClient(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             @RequestParam(defaultValue = "createAt") String sortBy,
                                                                             @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size, sort);
        Page<ProductResponseBasicDTO> productResponses = productService.getAllProducts(pageable);
        return ResponseEntity.ok().body(productResponses);
    }
    @GetMapping("/admin/{productId}")
    public ResponseEntity<ProductResponseDetailDTO> getProduct(@PathVariable String productId) {
        ProductResponseDetailDTO responseDetailDTO = productService.getProductById(productId);
        return ResponseEntity.ok(responseDetailDTO);
    }
    @DeleteMapping("/admin/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok().body("Product deleted successfully");
    }
    @GetMapping("admin/activeProducts")
    public ResponseEntity<Page<ProductResponseDetailDTO>> getAllActiveProducts(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size,
                                                                               @RequestParam(defaultValue = "createAt") String sortBy,
                                                                               @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductResponseDetailDTO> productResponses = productService.getAllActiveProducts(pageable);
        return ResponseEntity.ok().body(productResponses);
    }
}
