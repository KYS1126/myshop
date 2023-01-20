package com.example.myshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.myshop.DTO.ItemFormDto;
import com.example.myshop.DTO.ItemImgDto;
import com.example.myshop.DTO.ItemSearchDto;
import com.example.myshop.entity.Item;
import com.example.myshop.entity.ItemImg;
import com.example.myshop.repositoty.ItemImgRepository;
import com.example.myshop.repositoty.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImgService itemImgService;
	private final ItemImgRepository itemImgRepository;
	
	//상품등록
	public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
		
		//상품등록
		Item item = itemFormDto.createItem();
		itemRepository.save(item);
		
		//이미지등록
		for (int i=0; i<itemImgFileList.size(); i++) {
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			
			if(i == 0) { //첫번째 이미지 일때 대표 상품 이미지 여부를 지정
				itemImg.setRepimgYn("Y");
			} else {
				itemImg.setRepimgYn("N");
			}
			
			itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
		}
		
		return item.getId();
	}
	
	//상품 가져오기 find
	@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능 향상
	public ItemFormDto getItemDtl(Long itemId) {
		//1.item_img테이블의 이미지를 가져온다
		List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
		List<ItemImgDto> itemImgDtoList = new ArrayList<>();
		
		//엔티티 객체를 DTO객체로 변환
		for(ItemImg itemImg : itemImgList) {
			//엔티티에서 > 뷰딴으로 데이터 변환, 앞단에서 엔티티로 변환해주는 과정
			//아이템 이미지 엔티티,아이템 이미지디티오를 각각 지정해주면 알아서 매핑 해준다
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
			itemImgDtoList.add(itemImgDto);
		}
		
		//2.item 테이블에 있는 데이터를 가져온다.
		Item item = itemRepository.findById(itemId)
								  .orElseThrow(EntityNotFoundException::new);
		//엔티티 객체를 DTO객체로 변환
		ItemFormDto itemFormDto  = ItemFormDto.of(item);
		
		//상품의 이미지 정보를 넣어준다.
		itemFormDto.setItemImgDtoList(itemImgDtoList);
		
		//join 관계라 얘만 가져와도 img 테이블도 가져온다.
		return itemFormDto;
	}
	
	//상품 수정
	public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
		Item item = itemRepository.findById(itemFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
		
		item.updateItem(itemFormDto);
		
		List<Long> itemImgIds = itemFormDto.getItemImgIds(); //상품 이미지 아이디 리스트를 조회
		
		for (int i=0; i<itemImgFileList.size(); i++) {
			itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
		}
		return item.getId();
	}
	
	//상품 리스트 가져오기
	@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능 향상
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}
	
	
	
}
