package com.example.myshop.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myshop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
