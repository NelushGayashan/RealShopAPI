package com.RealShopAPI.repositories;

import com.RealShopAPI.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by category
    List<Product> findByCategory(String category);

    // Find products by title containing a specific string (case-insensitive search)
    List<Product> findByTitleContainingIgnoreCase(String title);

    // Find products with a price greater than a specific amount
    @Query("SELECT p FROM Product p WHERE p.price > :price")
    List<Product> findExpensiveProducts(@Param("price") Double price);

    // Find all distinct categories
    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategories();
}
