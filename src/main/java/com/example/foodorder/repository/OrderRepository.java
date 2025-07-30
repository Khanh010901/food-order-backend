package com.example.foodorder.repository;

import com.example.foodorder.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.code = :code ")
    public Order getOrderByCode(@Param("code") String givenCode);
    public List<Order> getByUserId(Long id);
}
