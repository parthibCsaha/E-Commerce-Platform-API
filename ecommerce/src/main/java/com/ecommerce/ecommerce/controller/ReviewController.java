package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.entities.Review;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.repo.ProductRepository;
import com.ecommerce.ecommerce.repo.ReviewRepository;
import com.ecommerce.ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/{pid}/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long pid) {
        return ResponseEntity.ok(reviewRepo.findByProductId(pid));
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@PathVariable Long pid,
                                            @AuthenticationPrincipal UserDetails ud,
                                            @RequestBody Review review) {
        User u = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        Product p = productRepo.findById(pid).orElseThrow();
        review.setUsers(u);
        review.setProduct(p);
        return ResponseEntity.ok(reviewRepo.save(review));
    }
}
