package com.tttn.demowebsite.order;

import com.tttn.demowebsite.responses.OrderListResponse;
import com.tttn.demowebsite.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    void createOrder(OrderDTO orderDTO);

    Order getOrder(Long id);

    void updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<Order> findByUserId(long userId);

    OrderListResponse getAllOrders(int page, int limit);

    void updateOrderStatus(Long orderId, String status);
}
