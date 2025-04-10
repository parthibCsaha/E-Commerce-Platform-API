package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entities.Coupon;
import com.ecommerce.ecommerce.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponRepository couponRepo;

    @GetMapping("/{code}")
    public ResponseEntity<Coupon> validateCoupon(@PathVariable String code) {
        Coupon c = couponRepo.findById(code).orElseThrow();
        if (!c.isActive() || c.getExpiresAt().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(c);
    }

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon c) {
        return ResponseEntity.ok(couponRepo.save(c));
    }

}
