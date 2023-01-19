package com.example.myshop.DTO;

import org.modelmapper.ModelMapper;

import com.example.myshop.entity.ItemImg;

import lombok.*;

@Getter
@Setter
public class ItemImgDto {
	
	private Long id;
	
	private String imgName; //이미지 파일명
	
	private String oriImgName; //원본 이미지
	
	private String ImgUrl; //이미지 조회경로
	
	private String repimgYn; //대표 이미지 여부
	
	private static ModelMapper modelMapper = new ModelMapper(); //멤버변수로 모델맵퍼를 추가
	
	public static ItemImgDto of(ItemImg itemImg) { //ModelMapper설정법
		//엔티티에서 > 뷰딴으로 데이터 변환, 앞단에서 엔티티로 변환해주는 과정
		return modelMapper.map(itemImg, ItemImgDto.class); //아이템 이미지 엔티티,아이템 이미지디티오를 각각 지정해주면 알아서 매핑 해준다.

	}
}


