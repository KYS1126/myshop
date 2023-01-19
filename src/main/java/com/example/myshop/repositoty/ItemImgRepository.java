package com.example.myshop.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myshop.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
	
}
