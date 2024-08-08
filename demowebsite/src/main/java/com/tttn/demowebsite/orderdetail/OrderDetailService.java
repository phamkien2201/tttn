package com.tttn.demowebsite.orderdetail;

import com.tttn.demowebsite.order.Order;
import com.tttn.demowebsite.order.OrderRepository;
import com.tttn.demowebsite.product.Product;
import com.tttn.demowebsite.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void createOrderDetail(OrderDetailDTO orderDetailDTO) {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("OrderId not found"));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("ProductId not found"));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(orderDetailDTO.getPrice())
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .build();
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find OrderDetail"));
    }

    @Override
    public void updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO){
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find orderdetail"));
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("cannot find order"));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(()-> new IllegalArgumentException("cannot find product"));
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
