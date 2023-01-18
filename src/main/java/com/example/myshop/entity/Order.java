package com.example.myshop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.myshop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="orders") //테이블명 설정 , 설정을 따로 안하면 클래스 명으로 자동으로 입력됨
@Getter
@Setter
@ToString
public class Order {
	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy = GenerationType.AUTO) //p키 생성 전략, p키를 자동으로 만들어준다
	private Long id;
	
	//자식 테이블에 방향성과 조인컬럼을 설정해준다.
	@ManyToOne(fetch = FetchType.LAZY) //1:1 방향성이라고 지정해주는 것 //멤버가 부모 테이블이고 카트가 자식 테이블
	@JoinColumn(name = "member_id") //조인 관계에 있는 컬럼설정	
	private Member member;
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; //주문상태
	
	//오더 입장에서 오더 아이템이 여러개이다.
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) //OrderItem의 Order에 의해 관리가 된다. cascade = CascadeType.ALL - 영속성 전이
	private List<OrderItem> orderItems = new ArrayList<>();
	
}
