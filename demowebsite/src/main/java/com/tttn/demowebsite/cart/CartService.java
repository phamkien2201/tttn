package com.tttn.demowebsite.cart;

import com.tttn.demowebsite.order.CartItemDTO;
import com.tttn.demowebsite.product.Product;
import com.tttn.demowebsite.product.ProductRepository;
import com.tttn.demowebsite.user.User;
import com.tttn.demowebsite.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void addProductToCart(Long userId, CartItemDTO cartItemDTO) {
        // Tìm User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tìm Product
        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tìm giỏ hàng của user
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>()); // Khởi tạo cartItems nếu giỏ hàng chưa có sản phẩm
            cartRepository.save(cart); // Lưu giỏ hàng ngay sau khi khởi tạo
            System.out.println("New cart created and saved: " + cart.getId());
        }

        // Thêm hoặc cập nhật CartItem
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemDTO.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            System.out.println("CartItem updated: " + cartItem.getId());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setPrice(product.getPrice()); // Lấy giá từ Product
            cart.getCartItems().add(cartItem);
            System.out.println("New CartItem added: " + cartItem.getId());
        }

        // Lưu giỏ hàng sau khi thêm/cập nhật sản phẩm
        cartRepository.save(cart);
        System.out.println("Cart saved after modification: " + cart.getId());
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    @Override
    public void addOrUpdateCartItem(Long userId, CartItemDTO cartItemDTO) {
        Cart cart = getCartByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user: " + userId);
        }

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItemDTO.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Tăng số lượng sản phẩm nếu đã tồn tại
            existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
        } else {
            Product product = productRepository.findById(cartItemDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + cartItemDTO.getProductId()));

            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(cartItemDTO.getQuantity())
                    .price(product.getPrice()) // Lấy giá từ Product
                    .build();

            cart.getCartItems().add(newItem);
        }

        cartRepository.save(cart); // Lưu giỏ hàng sau khi thay đổi
    }



    @Override
    public void decreaseCartItem(Long userId, Long productId, int amount) {
        Cart cart = getCartByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user: " + userId);
        }

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart: " + productId));

        int newQuantity = existingItem.getQuantity() - amount;
        if (newQuantity <= 0) {
            // Xóa sản phẩm nếu số lượng <= 0
            cart.getCartItems().remove(existingItem);
        } else {
            // Cập nhật số lượng nếu vẫn còn
            existingItem.setQuantity(newQuantity);
        }

        // Lưu giỏ hàng sau khi thay đổi
        cartRepository.save(cart);
    }



    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        Cart cart = getCartByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user: " + userId);
        }

        // Tìm CartItem dựa trên cartItemId
        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found: " + cartItemId));

        // Xóa item từ giỏ hàng
        cart.getCartItems().remove(itemToRemove);

        // Lưu giỏ hàng sau khi xóa
        cartRepository.save(cart);
    }


}
