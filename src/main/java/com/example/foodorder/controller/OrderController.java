package com.example.foodorder.controller;

import com.example.foodorder.dto.OrderDto;
import com.example.foodorder.model.Order;
import com.example.foodorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/orders")
public class OrderController {
        @Autowired
        private OrderService orderService;

        @PostMapping
        public ResponseEntity<Order> createOrder(@RequestBody OrderDto dto) {
        Order order = orderService.createOrder(dto);
        return ResponseEntity.ok(order);
        }
}
