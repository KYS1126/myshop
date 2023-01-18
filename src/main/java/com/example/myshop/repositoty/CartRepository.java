package com.example.myshop.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myshop.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
}
