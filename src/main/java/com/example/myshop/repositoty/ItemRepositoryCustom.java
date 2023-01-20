package com.example.myshop.repositoty;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.myshop.DTO.ItemSearchDto;
import com.example.myshop.entity.Item;

//쿼리DSL
//1.사용자 정의 인터페이스 만들기

public interface ItemRepositoryCustom {//ItemSearchDto 내가 검색한것 //Pageable 페이징 처리 해주는 객체
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
