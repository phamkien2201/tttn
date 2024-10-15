package com.tttn.demowebsite.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId); // Tìm giỏ hàng của người dùng dựa trên userId
}
