package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.entities.WishlistItem;
import com.ecommerce.ecommerce.repo.ProductRepository;
import com.ecommerce.ecommerce.repo.UserRepository;
import com.ecommerce.ecommerce.repo.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistItemRepository wishRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    public ResponseEntity<List<WishlistItem>> viewWishlist(@AuthenticationPrincipal UserDetails ud) {
        User u = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        return ResponseEntity.ok(wishRepo.findByUsersId(u.getId()));
    }

    @PostMapping
    public ResponseEntity<WishlistItem> addToWishlist(@AuthenticationPrincipal UserDetails ud,
                                                      @RequestBody WishlistItem wi) {
        User u = userRepo.findByUsername(ud.getUsername()).orElseThrow();
        Product p = productRepo.findById(wi.getProduct().getId()).orElseThrow();
        wi.setUsers(u);
        wi.setProduct(p);
        return ResponseEntity.ok(wishRepo.save(wi));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long id) {
        wishRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
