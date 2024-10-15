package com.tttn.demowebsite.cart;

import com.tttn.demowebsite.order.CartItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(
            @RequestParam Long userId,
            @RequestBody CartItemDTO cartItemDTO) {
        cartService.addProductToCart(userId, cartItemDTO);
        return ResponseEntity.ok("Product added to cart");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<String> addOrUpdateCartItem(
            @PathVariable Long userId,
            @RequestBody CartItemDTO cartItemDTO) {
        cartService.addOrUpdateCartItem(userId, cartItemDTO);
        return ResponseEntity.ok("Cart item updated");
    }

    // Giảm số lượng sản phẩm trong giỏ hàng
    @PostMapping("/user/{userId}/decrease")

    public ResponseEntity<String> decreaseCartItem(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam int amount) {
        cartService.decreaseCartItem(userId, productId, amount);
        return ResponseEntity.ok("Cart item quantity decreased");
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/user/{userId}/remove")
    public ResponseEntity<String> removeCartItem(
            @PathVariable Long userId,
            @RequestParam Long cartItemId) {
        cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.ok("Cart item removed");
    }

}
