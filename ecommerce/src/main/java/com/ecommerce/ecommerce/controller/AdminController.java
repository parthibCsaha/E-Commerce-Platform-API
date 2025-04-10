package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.repo.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

@RestController
@RequestMapping("/admin/products")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product p) {
        return ResponseEntity.ok(productRepository.save(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository
                .findById(id)
                .map(new Function<Product, ResponseEntity<Product>>() {
                    @Override
                    public ResponseEntity<Product> apply(Product product) {
                        product.setName(updatedProduct.getName());
                        product.setDescription(updatedProduct.getDescription());
                        product.setCategory(updatedProduct.getCategory());
                        product.setPrice(updatedProduct.getPrice());
                        product.setStock(updatedProduct.getStock());
                        return ResponseEntity.ok(productRepository.save(product));
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
