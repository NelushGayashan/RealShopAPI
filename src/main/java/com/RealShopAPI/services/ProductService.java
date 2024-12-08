package com.RealShopAPI.services;

import com.RealShopAPI.exceptions.ProductNotFoundException;
import com.RealShopAPI.models.Product;
import com.RealShopAPI.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Fetches all products from the repository.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Fetches a product by its ID.
     * Throws ProductNotFoundException if the product does not exist.
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
    }

    /**
     * Fetches all distinct categories from the repository.
     */
    public List<String> getAllCategories() {
        return productRepository.findDistinctCategories();
    }

    /**
     * Fetches products by category.
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    /**
     * Creates a batch of products in the repository.
     */
    public List<Product> createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    /**
     * Creates a single product in the repository.
     */
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Updates an existing product in the repository.
     * Throws ProductNotFoundException if the product does not exist.
     */
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));

        // Update product fields
        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setImage(updatedProduct.getImage());
        existingProduct.setCategory(updatedProduct.getCategory());

        return productRepository.save(existingProduct);
    }

    /**
     * Deletes a product by its ID.
     * Throws ProductNotFoundException if the product does not exist.
     */
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }
}
