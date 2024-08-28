package com.uh.herts.CartServiceAPI.mapper;

import com.uh.herts.CartServiceAPI.dto.CartDTO;
import com.uh.herts.CartServiceAPI.dto.CartItemDTO;
import com.uh.herts.CartServiceAPI.entity.Cart;
import com.uh.herts.CartServiceAPI.entity.CartItem;
import com.uh.herts.CartServiceAPI.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartDTO toCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setUserId(cart.getUser().getUserId());
        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                .map(CartMapper::toCartItemDTO)
                .collect(Collectors.toList());
        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }

    public static CartItemDTO toCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        cartItemDTO.setProductId(cartItem.getProduct().getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        return cartItemDTO;
    }

    public static CartItem toCartItem(CartItemDTO cartItemDTO, Cart cart, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        return cartItem;
    }
}