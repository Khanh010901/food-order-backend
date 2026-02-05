package com.example.foodorder.service;

import com.example.foodorder.dto.*;
import com.example.foodorder.mapper.FoodMapper;
import com.example.foodorder.model.Food;
import com.example.foodorder.model.Order;
import com.example.foodorder.model.OrderItem;
import com.example.foodorder.model.User;
import com.example.foodorder.repository.FoodRepository;
import com.example.foodorder.repository.OrderRepository;
import com.example.foodorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodMapper foodMapper;
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    public Order findById(long id) {
        return (Order)orderRepository.findById(id).get();
    }

    public Order createOrder(OrderDto orderdto) {

        Order o = new Order();
            o.setCode(generateOrderCode());
            o.setOrderDate(LocalDateTime.now());
            User user = userRepository.findByUsername(orderdto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + orderdto.getUsername()));
            o.setUserId(user.getId());
            o.setAddress(orderdto.getAddress());
            o.setPhone(orderdto.getPhone());
            o.setStatus("Pending");
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

    public List<OrderDtoResponse> getOrderByUsername(String username) {

        List<Object[]> rows =  orderRepository.getOrdersByUserId(userRepository.findByUsername(username).get().getId());
        Map<Long, OrderDtoResponse> orderMap = new LinkedHashMap<>();

        for(Object[] row : rows){
        Long id = (Long) row[0];
        OrderDtoResponse orderDtoResponse = orderMap.get(id);
        if(orderDtoResponse == null) {
            orderDtoResponse = new OrderDtoResponse();
            orderDtoResponse.setOrderId(id);
            orderDtoResponse.setAddress((String) row[1]);
            orderDtoResponse.setCode((String) row[2]);
            orderDtoResponse.setOrderDate(((Timestamp) row[3]).toLocalDateTime());
            orderDtoResponse.setPhone((String) row[4]);
            orderDtoResponse.setTotalPrice((Double) row[5]);
            orderDtoResponse.setStatus((String) row[6]);
            orderDtoResponse.setUsername((String) row[7]);
            orderMap.put(id, orderDtoResponse);
        }

            OrderItemDtoResponse itemDtoResponse = new OrderItemDtoResponse();
                Food food = foodRepository.findById((Long) row[10]).get();
                    itemDtoResponse.setFood(foodMapper.toDtoManual(food));
                    itemDtoResponse.setPrice((Double)row[8]);
                    itemDtoResponse.setQuantity((Integer)row[9]);
                    orderDtoResponse.getItems().add(itemDtoResponse);
        }
        return new ArrayList<>(orderMap.values());
    }

    public List<OrderDtoResponse> getAllOrders() {
            List<Order> orders = orderRepository.findAll();
            Map<Long, OrderDtoResponse> orderMap = new LinkedHashMap<>();
            for(Order order : orders) {
                OrderDtoResponse orderDtoResponse = new OrderDtoResponse();
                orderDtoResponse.setOrderId(order.getId());
                orderDtoResponse.setAddress(order.getAddress());
                orderDtoResponse.setCode(order.getCode());
                orderDtoResponse.setOrderDate(order.getOrderDate());
                orderDtoResponse.setPhone(order.getPhone());
                orderDtoResponse.setTotalPrice(order.getTotalPrice());
                orderDtoResponse.setStatus(order.getStatus());
                orderDtoResponse.setUsername(userRepository.findById(order.getUserId()).get().getUsername());
                orderMap.put(order.getId(), orderDtoResponse);

            }

        return new ArrayList<>(orderMap.values());
    }

    private String generateOrderCode() {
        return "OD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public Order  updateStatus(OrderDto dto) {
        int updated = orderRepository.orderStatusUpdate(dto.getId(), dto.getStatus());
        if (updated == 0) {
            throw new RuntimeException("Order not found");
        }

        return orderRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Order not found after update"));
    }
}
