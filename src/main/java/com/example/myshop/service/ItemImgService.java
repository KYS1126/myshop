package com.example.myshop.service;

import javax.transaction.Transactional;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.myshop.entity.ItemImg;
import com.example.myshop.repositoty.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
	
	@Value("${itemImgLocation}")
	private String itemImgLocation; //C:/shop/item
	
	private final ItemImgRepository itemImgRepository;
	
	private final FileService fileService;
											//MultipartFile 스프링에서 파일 관리하게 편하게 해주는 클래스
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename(); //파일 이름이 담겨있음
		String imgName = "";
		String imgUrl = "";
		
		//파일 업로드
		//문자열이 널이거나 비어있을때
		if (!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item" + imgName;
		}
		
		//상품 이미지 정보 저장
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);
		itemImgRepository.save(itemImg);
		
		
		
		
	}
	
	
}
