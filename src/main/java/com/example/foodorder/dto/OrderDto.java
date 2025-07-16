package com.example.foodorder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long userId;
    private String address;
    private String phone;
    @JsonProperty("items")
    private List<OrderItemDto> orderItems;
}
