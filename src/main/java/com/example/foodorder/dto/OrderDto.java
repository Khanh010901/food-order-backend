package com.example.foodorder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String username;
    private String address;
    private String phone;
    private String status;
    private List<OrderItemDto> items;
}
