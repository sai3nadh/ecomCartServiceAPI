package com.uh.herts.CartServiceAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private int cartItemId;
    private int productId;
    private int quantity;

}
