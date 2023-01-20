package com.example.myshop.repositoty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myshop.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
	List<ItemImg> findByItemIdOrderByIdAsc(Long itemId); //셀렉트문, 아이템아이디로 검색 할꺼임
}
