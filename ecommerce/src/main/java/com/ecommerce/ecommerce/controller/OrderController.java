package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entities.CartItem;
import com.ecommerce.ecommerce.entities.Order;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.repo.CartItemRepository;
import com.ecommerce.ecommerce.repo.OrderRepository;
import com.ecommerce.ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<CartItem> items = cartItemRepository.findByUsersId(user.getId());
        if (items.isEmpty()) throw new RuntimeException("Cart is empty");
        BigDecimal totalPrice = items.stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setUsers(user);
        order.setTotalAmount(totalPrice);
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        cartItemRepository.deleteAll(items);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Order> orders = orderRepository.findByUsersId(user.getId());
        return ResponseEntity.ok(orders);
    }

}
