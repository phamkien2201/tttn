package com.tttn.demowebsite.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tttn.demowebsite.order.Order;
import com.tttn.demowebsite.orderdetail.OrderDetail;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;
    @JsonProperty("note")
    private String note;
    @JsonProperty("order_date")
    private Date orderDate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("total_money")
    private double totalMoney;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("order_details")
    private List<OrderDetail> orderDetails;

    public static OrderResponse fromOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .fullName(order.getFullName())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .note(order.getNote())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalMoney(order.getTotalMoney())
                .shippingAddress(order.getShippingAddress())
                .shippingMethod(order.getShippingMethod())
                .orderDetails(order.getOrderDetails())
                .build();
    }
}
