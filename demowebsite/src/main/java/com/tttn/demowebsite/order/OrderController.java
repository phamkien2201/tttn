package com.tttn.demowebsite.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public void createOrder( @Valid @RequestBody OrderDTO orderDTO) {
            orderService.createOrder(orderDTO);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Order>> getOrders(
            @Valid @PathVariable("user_id") Long userId) {
        List<Order> orders = orderService.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
            @Valid @PathVariable("id") Long orderId) {
        Order existingOrder = orderService.getOrder(orderId);
        return ResponseEntity.ok(existingOrder);
    }

    //cong viec cua admin
    @PutMapping("/{id}")
    public void updateOrder(
            @Valid @PathVariable long id,
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@Valid @PathVariable Long id) {
        //xoa mem => cap nhat truong actice = false
        orderService.deleteOrder(id);
    }
}
