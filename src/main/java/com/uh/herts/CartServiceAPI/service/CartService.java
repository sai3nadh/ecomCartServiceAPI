package com.uh.herts.CartServiceAPI.service;


import com.uh.herts.CartServiceAPI.dto.CartDTO;
import com.uh.herts.CartServiceAPI.dto.CartItemDTO;

public interface CartService {
    CartDTO getCartByUserId(int userId);
    CartDTO addItemToCart(int userId, CartItemDTO cartItemDTO);
    CartDTO removeItemFromCart(int userId, Long productId);
    void clearCart(int userId);

}