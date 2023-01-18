package com.example.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.myshop.constant.ItemSellStatus;

import groovy.transform.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart") //테이블명 설정 , 설정을 따로 안하면 클래스 명으로 자동으로 입력됨
@Getter
@Setter
@ToString
public class Cart {
	
	
	@Id
	@Column(name = "cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//자식 테이블에 방향성과 조인컬럼을 설정해준다.
	@OneToOne(fetch = FetchType.LAZY) //1:1 방향성이라고 지정해주는 것 //멤버가 부모 테이블이고 카트가 자식 테이블
	@JoinColumn(name = "member_id") //조인 관계에 있는 컬럼설정	
	private Member member;
	
	
	
	
}
