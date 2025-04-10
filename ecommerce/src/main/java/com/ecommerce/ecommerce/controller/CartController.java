package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entities.CartItem;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.repo.CartItemRepository;
import com.ecommerce.ecommerce.repo.ProductRepository;
import com.ecommerce.ecommerce.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<CartItem>> viewCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(cartItemRepository.findByUsersId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CartItem cartItem) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow();
        if (cartItem.getQuantity() > product.getStock()) {
            throw new RuntimeException("Out of stock");
        }
        cartItem.setUsers(user);
        return ResponseEntity.ok(cartItemRepository.save(cartItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CartItem> removeFromCart(@PathVariable Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow();
        cartItemRepository.delete(cartItem);
        return ResponseEntity.ok(cartItem);
    }


}
