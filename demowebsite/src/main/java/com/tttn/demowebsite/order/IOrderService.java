package com.tttn.demowebsite.order;

import java.util.List;

public interface IOrderService {
    void createOrder(OrderDTO orderDTO);

    Order getOrder(Long id);

    void updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<Order> findByUserId(long userId);
}
