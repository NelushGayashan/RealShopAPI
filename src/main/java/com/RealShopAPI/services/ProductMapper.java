package com.RealShopAPI.services;

import com.RealShopAPI.dto.ProductDTO;
import com.RealShopAPI.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Convert ProductDTO to Product entity
    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        product.setCategory(productDTO.getCategory());
        return product;
    }

    // Convert Product entity to ProductDTO
    public ProductDTO toDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setImage(product.getImage());
        productDTO.setCategory(product.getCategory());
        return productDTO;
    }
}
