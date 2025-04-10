package com.ecommerce.ecommerce.repo;

import com.ecommerce.ecommerce.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {
}