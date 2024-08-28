package com.uh.herts.CartServiceAPI.repository;

import com.uh.herts.CartServiceAPI.entity.Cart;
import com.uh.herts.CartServiceAPI.entity.CartItem;
import com.uh.herts.CartServiceAPI.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
        Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}