package com.uh.herts.CartServiceAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private int cartId;
    private int userId;
    private List<CartItemDTO> cartItems;

}
