package com.example.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.myshop.constant.ItemSellStatus;

import lombok.*;

@Entity
@Table(name="item") //테이블명 설정 , 설정을 따로 안하면 클래스 명으로 자동으로 입력됨
@Getter
@Setter
@ToString
public class Item {
	
	//not null이 아닐때는 필드(예 int - Integer) 타입을 객체로 지정해야 한다.
	
	
	@Id //p키
	@Column(name = "item_id") // 컬럼명
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //상품코드
	
	@Column(nullable = false,length = 50) //not null, 최대 글자 수 50
	private String itemNm; //상품명
	
	@Column(nullable = false, name = "price")
	private int price; //가격
	
	@Column(nullable = false)
	private int stockNumber; //재고수량
	
	@Lob //큰 타입
	@Column(nullable = false)
	private String itemDetail; //상품 상세 설명
	
	@Enumerated(EnumType.STRING) //이름 그대로를 저장한다. EnumType.ORDINAL = 인덱스 번호로 저장한다
	private ItemSellStatus itemSellStatus; //상품 판매상태
	
	private LocalDateTime regTime; //등록 시간
	
	private LocalDateTime updateTime; //수정 시간
	
	
	
	
	
	
}
