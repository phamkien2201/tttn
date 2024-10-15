package com.tttn.demowebsite.order;

import com.tttn.demowebsite.responses.OrderListResponse;
import com.tttn.demowebsite.responses.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<OrderResponse> getOrder(
            @Valid @PathVariable("id") Long orderId) {
        Order existingOrder = orderService.getOrder(orderId);
        return ResponseEntity.ok(OrderResponse.fromOrder(existingOrder));
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

    @GetMapping("")
    public ResponseEntity<OrderListResponse> getAllOrders(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
            if (page == null) {
                page = 0;
            }
            if (limit == null) {
                limit = 10;
            }
        OrderListResponse orderListResponse = orderService.getAllOrders(page, limit);
        return ResponseEntity.ok(orderListResponse);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable("id") Long orderId,
            @RequestParam("status") String status) {
        try {
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok("Order status updated to: " + status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
