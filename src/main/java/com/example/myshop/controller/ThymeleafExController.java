package com.example.myshop.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myshop.DTO.ItemDto;

@Controller //컨트롤러의 역할을 한다고 선언해준다.
@RequestMapping(value = "/thymeleaf") //request url의 경로를 지정해준는애
public class ThymeleafExController {
	
	@GetMapping(value = "/ex01") //주소가 뒤에 ex01이 붙으면 해당 메소드를 실행한다.
	public String thymeleafEx01(Model model) { //setAttribute와 역할이 비슷하다.
		model.addAttribute("data", "타임리프 예제 입니다.");  //key와 값으로 이루어져 있음.
		return "thymeleafEx/thymeleafEx01"; //request가 들어오고 메소드가 실행하면 templates 아래에 경로에서 해당 html을 찾아준다.
	}
	
	@GetMapping(value = "/ex02")
	public String thymeleafEx02(Model model) { //setAttribute와 같은 역할!!!!
		ItemDto itemDto = new ItemDto();
		
		itemDto.setItemNm("테스트 상품");
		itemDto.setPrice(10000);
		itemDto.setItemDetail("테스트 상품 상세설명");
		itemDto.setRegTime(LocalDateTime.now()); 
		
		model.addAttribute("itemDto",itemDto);
		
		return "thymeleafEx/thymeleafEx02";
	}
	
	@GetMapping(value = "/ex03")
	public String thymeleafEx03(Model model) { //setAttribute와 같은 역할!!!!
		
		List<ItemDto> itemDtoList = new ArrayList<ItemDto>();
		
		for(int i=1; i<=10; i++) {
			ItemDto itemDto = new ItemDto();
			
			itemDto.setItemNm("테스트 상품" + i);
			itemDto.setPrice(10000 + i);
			itemDto.setItemDetail("테스트 상품 상세설명" + i);
			itemDto.setRegTime(LocalDateTime.now());
			
			itemDtoList.add(itemDto);
		}
		
		model.addAttribute("itemDtoList",itemDtoList);
		
		return "thymeleafEx/thymeleafEx03";
	}
	
	@GetMapping(value = "/ex04")
	public String thymeleafEx04(Model model) { //setAttribute와 같은 역할!!!!
		
		List<ItemDto> itemDtoList = new ArrayList<ItemDto>();
		
		for(int i=1; i<=10; i++) {
			ItemDto itemDto = new ItemDto();
			
			itemDto.setItemNm("테스트 상품" + i);
			itemDto.setPrice(10000 + i);
			itemDto.setItemDetail("테스트 상품 상세설명" + i);
			itemDto.setRegTime(LocalDateTime.now());
			
			itemDtoList.add(itemDto);
		}
		
		model.addAttribute("itemDtoList",itemDtoList);
		
		return "thymeleafEx/thymeleafEx04";
	}
	
	@GetMapping(value = "/ex05")
	public String thymeleafEx05(Model model) { //setAttribute와 같은 역할!!!!
		return "thymeleafEx/thymeleafEx05";
	}
	
	
	
}
