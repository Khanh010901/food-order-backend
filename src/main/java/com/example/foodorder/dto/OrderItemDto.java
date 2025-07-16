package com.example.foodorder.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long foodId;
    private Integer quantity;
}
