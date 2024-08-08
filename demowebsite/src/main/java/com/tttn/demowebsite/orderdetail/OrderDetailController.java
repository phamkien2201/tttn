package com.tttn.demowebsite.orderdetail;

import com.tttn.demowebsite.order.Order;
import com.tttn.demowebsite.responses.OrderDetailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order_details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public void createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        orderDetailService.createOrderDetail(orderDetailDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(orderDetail));
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetails(
            @Valid @PathVariable("orderId") Long orderId) {
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();
        return ResponseEntity.ok(orderDetails);
    }

    @PutMapping("/{id}")
    public void updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
            ) {
        orderDetailService.updateOrderDetail(id, orderDetailDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetail(
            @Valid @PathVariable("id") Long id
    ) {
        orderDetailService.deleteOrderDetail(id);
    }
}
