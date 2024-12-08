package com.RealShopAPI.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private Long productId;
    private int quantity;

    // Custom constructor to initialize productId and quantity
    public CartItem(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
