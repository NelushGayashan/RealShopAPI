package com.RealShopAPI.repositories;

import com.RealShopAPI.models.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Long userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems WHERE c.id = :id")
    Optional<Cart> findByIdWithItems(Long id);

    // New query to find carts within a date range
    Page<Cart> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
