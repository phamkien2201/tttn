package com.tttn.demowebsite.order;

import com.tttn.demowebsite.orderdetail.OrderDetail;
import com.tttn.demowebsite.orderdetail.OrderDetailRepository;
import com.tttn.demowebsite.product.Product;
import com.tttn.demowebsite.product.ProductRepository;
import com.tttn.demowebsite.responses.OrderListResponse;
import com.tttn.demowebsite.responses.OrderResponse;
import com.tttn.demowebsite.user.User;
import com.tttn.demowebsite.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with id" +orderDTO.getUserId()));
        //thu vien Model Mapper
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new IllegalStateException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        order.setTotalMoney(orderDTO.getTotalMoney());
        orderRepository.save(order);
        //
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            Long productId = cartItemDTO.getProductId();
            int quantity = cartItemDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Cannot find product with id" +productId));

            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(quantity);
            orderDetail.setPrice(product.getPrice());
            orderDetails.add(orderDetail);
        }

        orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    @Override
    public void updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find order with id" +id));
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Cannot find user with id" +id));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public OrderListResponse getAllOrders(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<Order> orderPage = orderRepository.findAll(pageRequest);

        List<OrderResponse> orderResponses = orderPage.getContent().stream()
                .map(OrderResponse::fromOrder)
                .collect(Collectors.toList());

        return new OrderListResponse(orderResponses, orderPage.getTotalPages());
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        // Kiểm tra nếu trạng thái mới là hợp lệ
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        // Cập nhật trạng thái đơn hàng
        order.setStatus(status);
        orderRepository.save(order);
    }

    private boolean isValidStatus(String status) {
        return OrderStatus.PENDING.equals(status) ||
                OrderStatus.PROCESSING.equals(status) ||
                OrderStatus.SHIPPED.equals(status) ||
                OrderStatus.DELIVERED.equals(status) ||
                OrderStatus.CANCELLED.equals(status);
    }

}
