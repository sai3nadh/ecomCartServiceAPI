package com.uh.herts.CartServiceAPI.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO {

    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;

    // Getters and Setters
}