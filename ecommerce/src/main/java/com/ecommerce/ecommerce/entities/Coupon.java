package com.ecommerce.ecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coupon {

    @Id
    @Column(nullable = false)
    private String code;

    private Integer discountPercent;

    private LocalDateTime expiresAt;

    private boolean active;

}
