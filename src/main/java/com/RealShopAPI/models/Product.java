package com.RealShopAPI.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    @Positive
    private Double price;

    @Size(max = 500)
    private String description;

    private String image = "default-image.png";

    @NotNull(message = "Category cannot be null")
    @Size(min = 3, max = 50, message = "Category name should be between 3 and 50 characters")
    private String category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructor for Mapper
    public Product(Long id, String title, Double price, String description, String image, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }
}
