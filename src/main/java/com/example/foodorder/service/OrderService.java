package com.example.foodorder.service;

import com.example.foodorder.dto.OrderDto;
import com.example.foodorder.dto.OrderItemDto;
import com.example.foodorder.model.Food;
import com.example.foodorder.model.Order;
import com.example.foodorder.model.OrderItem;
import com.example.foodorder.repository.FoodRepository;
import com.example.foodorder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FoodRepository foodRepository;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(OrderDto orderdto) {

        Order o = new Order();
            o.setCode(generateOrderCode());
            o.setOrderDate(LocalDateTime.now());
            o.setUserId(orderdto.getUserId());
            o.setAddress(orderdto.getAddress());
            o.setPhone(orderdto.getPhone());
             double total = 0.0;
            List<OrderItem> orderItems = new ArrayList<>();

            for(OrderItemDto item :orderdto.getItems()) {
            OrderItem oi = new OrderItem();
            Food food = foodRepository.findById(item.getFoodId()).orElseThrow(()->new RuntimeException("Food not found"));
            oi.setFood(food);
            oi.setQuantity(item.getQuantity());
            oi.setPrice(food.getPrice() * oi.getQuantity());
            oi.setOrder(o);
            orderItems.add(oi);
            total += (food.getPrice() * oi.getQuantity());
            };
            o.setOrderItems(orderItems);
            o.setTotalPrice(total);
        return orderRepository.save(o);
    }


    private String generateOrderCode() {
        return "OD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

}
