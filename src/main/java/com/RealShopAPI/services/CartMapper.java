package com.RealShopAPI.services;

import com.RealShopAPI.models.Cart;
import com.RealShopAPI.dto.CartDTO;
import com.RealShopAPI.models.CartItem;
import com.RealShopAPI.dto.CartItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartMapper {

    public CartDTO toDto(Cart cart) {
        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                .map(cartItem -> new CartItemDTO(cartItem.getProductId(), cartItem.getQuantity()))
                .collect(Collectors.toList());
    
        // Pass LocalDateTime directly instead of converting to String
        return new CartDTO(cart.getId(), cart.getUserId(), cart.getCreatedAt(), cartItemDTOs);
    }    

    public Cart toEntity(CartDTO cartDTO) {
        List<CartItem> cartItems = cartDTO.getProducts().stream()
                .map(cartItemDTO -> new CartItem(cartItemDTO.getProductId(), cartItemDTO.getQuantity()))
                .collect(Collectors.toList());

        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setUserId(cartDTO.getUserId());
        cart.setCartItems(cartItems);
        return cart;
    }
}
