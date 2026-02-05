package com.example.foodorder.controller;

import com.example.foodorder.dto.OrderDto;
import com.example.foodorder.dto.OrderDtoResponse;
import com.example.foodorder.model.Order;
import com.example.foodorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDtoResponse>> getAllOrders() {
        List<OrderDtoResponse> orderDtoResponse = orderService.getAllOrders();
        return new ResponseEntity<>(orderDtoResponse, HttpStatus.OK);
    }
    @PostMapping("/order/add")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto dto) {
        Order order = orderService.createOrder(dto);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/order/{username}")
    public ResponseEntity<?> getOrdersByUsername(@PathVariable String username) {
        return ResponseEntity.ok(orderService.getOrderByUsername(username));
    }

    @PostMapping("/order/status/update")
    public ResponseEntity<?> updateOrderStatus(@RequestBody OrderDto dto) {
        return ResponseEntity.ok(orderService.updateStatus(dto));
    }
}
