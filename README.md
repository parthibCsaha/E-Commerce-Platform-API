# E‑Commerce Platform API

A full‑featured, secure, role‑based e‑commerce REST API built with Spring Boot, Spring Data JPA, Spring Security (with JWT), and PostgreSQL. This project demonstrates key functionalities such as user authentication, product catalog management, shopping cart handling, order placement, wishlist management, coupon validation, and product reviews. It’s designed to showcase production‑ready code with two roles (CUSTOMER and ADMIN).

---

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
  - [UML Diagram of Entity Relationships](#uml-diagram-of-entity-relationships)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Testing with Postman](#testing-with-postman)
- [License](#license)

---

## Features

- **User Authentication & Role Management:**  
  - Register and log in with JWT authentication.
  - Role‑based access control: ADMIN and CUSTOMER.
  
- **Product Catalog:**  
  - Public endpoints to browse products.
  - ADMIN endpoints to create, update, and delete products.
  
- **Shopping Cart & Order Processing:**  
  - Add products to a cart.
  - Place an order (which calculates total amount and clears the cart).
  
- **Wishlist & Reviews:**  
  - Manage a wishlist.
  - Submit product reviews and ratings.
  
- **Coupon Codes:**  
  - Validate and apply coupons during checkout.
  
- **Robust Security:**  
  - Secured APIs with Spring Security and JWT.
  - Passwords encoded with BCrypt.
  
---

## Architecture

This project is built with a layered architecture:

- **Controller Layer:** Exposes REST endpoints.
- **Service Layer:** (Optional for further refactoring) Contains business logic.
- **Repository Layer:** Uses Spring Data JPA to persist data in PostgreSQL.
- **Security:** Configured to protect endpoints based on user roles.
- **DTOs:** Used for clean API contracts.

### UML Diagram of Entity Relationships

Below is a UML diagram (using Mermaid syntax) representing the key entity relationships in this project:

```mermaid
classDiagram
    class User {
        +Long id
        +String username
        +String password
        +String email
        +String role
    }
    class Product {
        +Long id
        +String name
        +String description
        +BigDecimal price
        +Integer stock
        +String category
    }
    class CartItem {
        +Long id
        +Integer quantity
    }
    class Order {
        +Long id
        +LocalDateTime orderDate
        +BigDecimal totalAmount
        +String couponCode
        +String status
    }
    class OrderItem {
        +Long id
        +Integer quantity
        +BigDecimal unitPrice
    }
    class WishlistItem {
        +Long id
    }
    class Coupon {
        +String code
        +Integer discountPercent
        +LocalDate expiresAt
        +Boolean active
    }
    class Review {
        +Long id
        +Integer rating
        +String comment
        +LocalDateTime createdAt
    }

    User "1" -- "many" CartItem : owns >
    User "1" -- "many" Order : places >
    User "1" -- "many" WishlistItem : owns >
    User "1" -- "many" Review : writes >

    Product "1" -- "many" CartItem : appears in >
    Product "1" -- "many" OrderItem : part of >
    Product "1" -- "many" Review : receives >

    Order "1" -- "many" OrderItem : contains >

