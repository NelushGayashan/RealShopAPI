package com.RealShopAPI.services;

import com.RealShopAPI.exceptions.CartNotFoundException;
import com.RealShopAPI.exceptions.InvalidUserIdException;
import com.RealShopAPI.models.Cart;
import com.RealShopAPI.repositories.CartRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts(String limit, String sort, String startdate, String enddate) {
        // Create PageRequest for pagination and sorting
        PageRequest pageRequest = PageRequest.of(0, Integer.parseInt(limit));

        LocalDateTime start = (startdate != null) ? LocalDateTime.parse(startdate) : null;
        LocalDateTime end = (enddate != null) ? LocalDateTime.parse(enddate) : null;

        // Apply filters
        if (start != null && end != null) {
            return cartRepository.findByCreatedAtBetween(start, end, pageRequest).getContent();
        }
        // Apply sorting if needed
        return cartRepository.findAll(pageRequest).getContent();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID " + id));
    }

    @Transactional
    public Cart createCart(Cart cart) {
        if (cart.getUserId() == null || cart.getUserId() <= 0) {
            throw new InvalidUserIdException("User ID cannot be null or less than or equal to zero.");
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCart(Long id, Cart updatedCart) {
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID " + id));

        existingCart.setUserId(updatedCart.getUserId());
        existingCart.setCartItems(updatedCart.getCartItems());
        return cartRepository.save(existingCart);
    }

    @Transactional
    public void deleteCart(Long id) {
        cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID " + id));
        cartRepository.deleteById(id);
    }
}
