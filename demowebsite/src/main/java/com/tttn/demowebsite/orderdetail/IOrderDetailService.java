package com.tttn.demowebsite.orderdetail;


import java.util.List;

public interface IOrderDetailService {

    void createOrderDetail(OrderDetailDTO orderDetailDTO);

    OrderDetail getOrderDetail(Long id);

    void updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO);

    void deleteOrderDetail(Long id);

    List<OrderDetail> findByOrderId(long orderId);

}
