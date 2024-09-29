package com.uh.herts.CartServiceAPI.repository;

import com.uh.herts.CartServiceAPI.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser_UserId(int userId);


}