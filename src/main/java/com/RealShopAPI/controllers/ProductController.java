package com.RealShopAPI.controllers;

import com.RealShopAPI.dto.ProductDTO;
import com.RealShopAPI.exceptions.ProductNotFoundException;
import com.RealShopAPI.models.Product;
import com.RealShopAPI.services.ProductService;
import com.RealShopAPI.services.ProductMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    // Get all products with optional filtering, sorting, and limiting
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sortBy
    ) {
        List<Product> products;

        if (category != null) {
            products = productService.getProductsByCategory(category);
        } else {
            products = productService.getAllProducts();
        }

        // Default sort by id in ascending order
        if ("desc".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(Product::getId).reversed());
        } else if ("asc".equalsIgnoreCase(sort)) {
            products.sort(Comparator.comparing(Product::getId));
        }

        // Sorting by other fields, if specified
        if (sortBy != null) {
            if ("price".equalsIgnoreCase(sortBy)) {
                if ("desc".equalsIgnoreCase(sort)) {
                    products.sort(Comparator.comparing(Product::getPrice).reversed());
                } else {
                    products.sort(Comparator.comparing(Product::getPrice));
                }
            } else if ("title".equalsIgnoreCase(sortBy)) {
                if ("desc".equalsIgnoreCase(sort)) {
                    products.sort(Comparator.comparing(Product::getTitle).reversed());
                } else {
                    products.sort(Comparator.comparing(Product::getTitle));
                }
            }
        }

        // Apply the limit if it's provided
        if (limit != null && limit > 0) {
            products = products.stream().limit(limit).collect(Collectors.toList());
        }

        // Map products to ProductDTOs
        List<ProductDTO> productsDTO = products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productsDTO);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    // Get all available categories
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = productService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Get products by category with optional sorting and limiting
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String sort
    ) {
        List<Product> products = productService.getProductsByCategory(category);

        if ("desc".equalsIgnoreCase(sort)) {
            products.sort((a, b) -> b.getId().compareTo(a.getId()));
        } else if ("asc".equalsIgnoreCase(sort)) {
            products.sort((a, b) -> a.getId().compareTo(b.getId()));
        }

        if (limit != null && limit > 0) {
            products = products.stream().limit(limit).collect(Collectors.toList());
        }

        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }

    // Create one or multiple products
    @PostMapping
    public ResponseEntity<List<ProductDTO>> createProducts(@RequestBody List<ProductDTO> productDTOs) {
        List<Product> products = productDTOs.stream()
                .map(productMapper::toEntity)
                .collect(Collectors.toList());

        List<Product> createdProducts = productService.createProducts(products);

        List<ProductDTO> createdProductDTOs = createdProducts.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(createdProductDTOs);
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(productMapper.toDto(updatedProduct));
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Exception handler for ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
