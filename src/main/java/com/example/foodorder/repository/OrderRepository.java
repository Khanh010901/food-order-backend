package com.example.foodorder.repository;

import com.example.foodorder.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.code = :code ")
    public Order getOrderByCode(@Param("code") String givenCode);
    @Query(value = "SELECT o.id, o.address,o.code,o.order_date AS OrderDate,o.phone,o.total_price TotalPrice, o.status, u.username, oi.price, oi.quantity,f.id AS foodId FROM orders o " +
            "join order_item oi on o.id = oi.order_id " +
            "join food f on f.id = oi.food_id " +
            "join users u on u.id = o.user_id " +
            "Where u.id= :id and o.status='PENDING'",nativeQuery = true)
    public List<Object[]> getOrdersByUserId(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET status = :status WHERE id = :id", nativeQuery = true)
    public int orderStatusUpdate(@Param("id") Long id, @Param("status") String status);

}
