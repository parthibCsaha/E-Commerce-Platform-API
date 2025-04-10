package com.ecommerce.ecommerce.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthRequest {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
