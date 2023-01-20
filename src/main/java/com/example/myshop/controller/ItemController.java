package com.example.myshop.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.myshop.DTO.ItemFormDto;
import com.example.myshop.DTO.ItemSearchDto;
import com.example.myshop.entity.Item;
import com.example.myshop.service.ItemService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;
	
	//상품등록 페이지를 보여주는 컨트롤러
	@GetMapping(value = "/admin/item/new")
	public String itemForm(Model model) {
		model.addAttribute("itemFormDto", new ItemFormDto());
		return "item/itemForm";
	}
	
	//상품을 등록하기
	@PostMapping(value = "/admin/item/new")
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model, 
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		//만약에 에러가 있다면
		if(bindingResult.hasErrors()) {
			return "item/itemForm";
		}
		
		//특별한 에러가 없으면
		//첫번쨰 이미지는 필수값이므로 있는지 검사하기
		if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "item/itemForm";
		}
		
		try {
			itemService.saveItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "item/itemForm";
		}
		
		return "redirect:/";
	}
	
	//수정 페이지 보기
	@GetMapping(value="admin/item/{itemId}")
	public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
		try {
															//겟방식으로 받은 변수가 매개변수로 들어감
			ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
			model.addAttribute(itemFormDto);
		}catch (EntityNotFoundException e) {
			//에러 발생시 보내줄 값
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			//앞단에서 객체를 받기 떄문에 일단 보내줘야함
			model.addAttribute("itemFormDto", new ItemFormDto()); //빈 객체
			return "item/itemForm";
		}		
		return "item/itemForm";
	}
	
	//상품 수정
	@PostMapping(value = "/admin/item/{itemId}")
	public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model, 
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		
		if(bindingResult.hasErrors()) {
			return "item/itemForm";
		}
		
		//특별한 에러가 없으면
		//첫번쨰 이미지는 필수값이므로 있는지 검사하기
		if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "item/itemForm";
		}
		
		try {
			itemService.updateItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage","상품 수정 중 에러가 발생하였습니다.");
			return "item/itemForm";
		}
		
		return "redirect:/";
		
	}
	
	//매핑을 두개 해준것, 매핑하는 주소가 여러개일때 넣는 방법이다.
	@GetMapping(value = {"/admin/items", "/admin/items/{page}"})
	public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		//url 경로에 페이지가 있으면 해당 페이지를 조회하도록 하고 페이지 번호가 없으면 0페이지를 조회하도록 한다.
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3); //of (죄회할 페이지의 번호, 한페이지당 조회할 페이지의 갯수)
		
		//list가 아니라 페이징 처리를한 객체기 떄문에 page에 담아야 한다.
		Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
		
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("MaxPage", 5);
		
		return "item/itemMng";
	}
	
	
}
