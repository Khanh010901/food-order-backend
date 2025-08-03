package com.example.foodorder.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;


@Data
public class OrderDtoResponse {
    private Long orderId;
    private String code;
    private String username;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private String address;
    private String phone;
    private String status;
    private List<OrderItemDtoResponse> items = new ArrayList<>();;
}
