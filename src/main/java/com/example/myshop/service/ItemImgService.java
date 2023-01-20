package com.example.myshop.service;

import javax.persistence.EntityNotFoundException;
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
	
	//이미지 업데이트 메소드
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
		//itemImgFile이 비어있지 않으면
		if(!itemImgFile.isEmpty()) {
			ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
					.orElseThrow(EntityNotFoundException::new);
			
			//기존 이미지 파일 삭제하기
			//savedItemImg에서 getImgName가 없으면
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
				//C:/shop/item/4c8fbc9f-777b-465e-933c-42999a797741.jpg
				fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
			}
			
			//이미지 파일 업로드
			String oriImgName = itemImgFile.getOriginalFilename(); //파일 이름이 담겨있음
			//수정된 이미지파일 업로드
			//문자열이 널이거나 비어있을때
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			String imgUrl = "/images/item" + imgName;
			
			//수정할떄는 save 안해줘도 상관없다 itemImgRepository.save(itemImg);
			//★savedItemImg 현재 영속상태(영속성 컨텍스트가 이 엔티티가 들어있다.)
			//데이터를 변경하는 것만으로 변경감지 기능이 동작하여 트랜젝션이 끝날떄 update쿼리가 실행된다.
			//->엔티티가 반드시 영속상태여야 한다.
			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
			
		}
	}
	
}
