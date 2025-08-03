package com.example.foodorder.dto;

import lombok.Data;

@Data
public class OrderItemDtoResponse {
    private FoodDto food;
    private Integer quantity;
    private Double price;
}
