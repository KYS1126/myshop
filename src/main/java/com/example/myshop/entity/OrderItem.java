package com.example.myshop.entity;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_item") //테이블명 설정 , 설정을 따로 안하면 클래스 명으로 자동으로 입력됨
@Getter
@Setter
@ToString
public class OrderItem {

	@Id
	@Column(name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//자식 테이블에 방향성과 조인컬럼을 설정해준다.
	@ManyToOne(fetch = FetchType.LAZY)  //(fetch = FetchType.LAZY) 지연로딩
	@JoinColumn(name = "item_id") //조인 관계에 있는 컬럼설정	
	private Item item;
		
	//자식 테이블에 방향성과 조인컬럼을 설정해준다.
	@ManyToOne(fetch = FetchType.LAZY)  //자기가 기준임
	@JoinColumn(name = "order_id") //조인 관계에 있는 컬럼설정	
	private Order order;
	
	private int orderPrice;
	
	private int count;
	
}
