package com.RealShopAPI.controllers;

import com.RealShopAPI.dto.CartDTO;
import com.RealShopAPI.exceptions.CartNotFoundException;
import com.RealShopAPI.services.CartService;
import com.RealShopAPI.services.CartMapper;
import com.RealShopAPI.models.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
@Validated
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> getAllCarts(
        @RequestParam(required = false) String limit,
        @RequestParam(required = false) String sort,
        @RequestParam(required = false) String startdate,
        @RequestParam(required = false) String enddate) {
        
        List<CartDTO> cartDTOs = cartService.getAllCarts(limit, sort, startdate, enddate)
                .stream()
                .map(cartMapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(cartDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart(
        @Valid @RequestBody CartDTO cartDTO, BindingResult result) {
        
        if (result.hasErrors()) {
            // Return a bad request response with a dummy CartDTO in case of validation errors
            return ResponseEntity.badRequest().body(new CartDTO());
        }
        
        Cart cart = cartMapper.toEntity(cartDTO);
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.ok(cartMapper.toDto(createdCart));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> updateCart(
        @PathVariable Long id, @Valid @RequestBody CartDTO cartDTO, BindingResult result) {
        
        if (result.hasErrors()) {
            // Return a bad request response with a dummy CartDTO in case of validation errors
            return ResponseEntity.badRequest().body(new CartDTO());
        }
        
        Cart cart = cartMapper.toEntity(cartDTO);
        Cart updatedCart = cartService.updateCart(id, cart);
        return ResponseEntity.ok(cartMapper.toDto(updatedCart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<CartDTO> handleCartNotFound(CartNotFoundException ex) {
        // Create a CartDTO to return with error details or a default empty CartDTO
        CartDTO errorCartDTO = new CartDTO();
        return ResponseEntity.status(404).body(errorCartDTO);
    }
}
