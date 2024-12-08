package com.RealShopAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // Add this annotation to generate the no-argument constructor
public class ProductDTO {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
}
