package com.uh.herts.CartServiceAPI.controller;



import com.uh.herts.CartServiceAPI.dto.CartDTO;
import com.uh.herts.CartServiceAPI.dto.CartItemDTO;
import com.uh.herts.CartServiceAPI.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "http://localhost:4200") // Angular's default port
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable int userId) {
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable int userId, @RequestBody CartItemDTO cartItemDTO) {
        CartDTO cartDTO = cartService.addItemToCart(userId, cartItemDTO);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartDTO> removeItemFromCart(@PathVariable int userId, @PathVariable Long productId) {
        CartDTO cartDTO = cartService.removeItemFromCart(userId, productId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable int userId) {
        cartService.clearCart(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}