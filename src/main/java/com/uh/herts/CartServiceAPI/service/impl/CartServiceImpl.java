package com.uh.herts.CartServiceAPI.service.impl;


import com.uh.herts.CartServiceAPI.dto.CartDTO;
import com.uh.herts.CartServiceAPI.dto.CartItemDTO;
import com.uh.herts.CartServiceAPI.dto.UserDTO;
import com.uh.herts.CartServiceAPI.entity.Cart;
import com.uh.herts.CartServiceAPI.entity.CartItem;
import com.uh.herts.CartServiceAPI.entity.Product;
import com.uh.herts.CartServiceAPI.entity.User;
import com.uh.herts.CartServiceAPI.exception.ResourceNotFoundException;
import com.uh.herts.CartServiceAPI.mapper.CartMapper;
import com.uh.herts.CartServiceAPI.repository.CartRepository;
import com.uh.herts.CartServiceAPI.repository.ProductRepository;
import com.uh.herts.CartServiceAPI.repository.CartItemRepository;
import com.uh.herts.CartServiceAPI.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private RestTemplate restTemplate;  // Add RestTemplate bean to your configuration



    @Transactional
    @Override
    public CartDTO getCartByUserId(int userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "user_id", String.valueOf(userId)));
        return CartMapper.toCartDTO(cart);
    }

    public CartDTO addItemToCartV1(int userId, CartItemDTO cartItemDTO) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseGet(() -> createNewCart(userId));

        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "product_id", String.valueOf( cartItemDTO.getProductId())));

        CartItem cartItem = CartMapper.toCartItem(cartItemDTO, cart, product);
        cart.getCartItems().add(cartItem);

        return CartMapper.toCartDTO(cartRepository.save(cart));
    }

    @Transactional
    @Override
    public CartDTO removeItemFromCart(int userId, Long productId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "user_id", String.valueOf(userId)));

        cart.getCartItems().removeIf(item -> item.getProduct().getProductId().equals(productId));

        return CartMapper.toCartDTO(cartRepository.save(cart));
    }

    @Transactional
    @Override
    public void clearCart(int userId) {
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "user_id",String.valueOf(userId)));

        cartRepository.delete(cart);
    }

    @Transactional
    @Override
    public CartDTO addItemToCart(int userId, CartItemDTO cartItemDTO) {

        User user = restTemplate.getForObject("http://13.40.219.97:8084/api/users/" + userId, User.class);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Product product = productRepository.findById(cartItemDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        // Retrieve the cart for the user
        Cart cart = cartRepository.findByUser_UserId(userId).orElse(new Cart());
        if (cart.getCartId() == 0) {
            cart.setUser(user);
            cartRepository.save(cart);
        }

        // Check if the product is already in the cart
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingCartItem.isPresent()) {
            // If the product is already in the cart, increase the quantity
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            // If the product is not in the cart, create a new CartItem
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartItemDTO.getQuantity());
            cartItemRepository.save(newCartItem);
        }

        // Return the updated CartDTO
        CartDTO cartDTO = mapCartToDTO(cart);
        return cartDTO;
    }

    private CartDTO mapCartToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setUserId(cart.getUser().getUserId());

        // Map CartItems to CartItemDTOs
        cartDTO.setCartItems(cart.getCartItems().stream().map(this::mapCartItemToDTO).collect(Collectors.toList()));

        return cartDTO;
    }
    private CartItemDTO mapCartItemToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCartItemId(cartItem.getCartItemId());
        cartItemDTO.setProductId(cartItem.getProduct().getProductId());
//        cartItemDTO.setProductName(cartItem.getProduct().getName());
        cartItemDTO.setQuantity(cartItem.getQuantity());

        return cartItemDTO;
    }

   private Cart createNewCart(int userId) {
       // Call the User Service to get user details
       String userServiceUrl = "http://13.40.219.97:8084/api/users/" + userId;
       ResponseEntity<UserDTO> response = restTemplate.getForEntity(userServiceUrl, UserDTO.class);

       if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
           UserDTO userDTO = response.getBody();

           // Now map the UserDTO to User entity
           User user = new User();
           user.setUserId(userDTO.getUserId());
           user.setUsername(userDTO.getUsername());
           user.setEmail(userDTO.getEmail());
           user.setFirstName(userDTO.getFirstName());
           user.setLastName(userDTO.getLastName());
           user.setCreatedAt(userDTO.getCreatedAt());

           Cart cart = new Cart();
           cart.setUser(user);  // Set the user object with full details
           return cartRepository.save(cart);
       } else {
           throw new RuntimeException("Failed to fetch user details for user ID: " + userId);
       }
   }
}