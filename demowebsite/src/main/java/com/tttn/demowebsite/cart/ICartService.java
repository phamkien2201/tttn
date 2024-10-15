package com.tttn.demowebsite.cart;

import com.tttn.demowebsite.order.CartItemDTO;

public interface ICartService {
    void addProductToCart(Long userId, CartItemDTO cartItemDTO);  // Sử dụng CartItemDTO
    Cart getCartByUserId(Long userId);
    void clearCart(Long userId);
    void addOrUpdateCartItem(Long userId, CartItemDTO cartItemDTO);
    void decreaseCartItem(Long userId, Long productId, int amount);
    void removeCartItem(Long userId, Long cartItemId);
}
