package com.example.myshop.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myshop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
