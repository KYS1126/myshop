package com.example.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해 사용하는 어노테이션
@MappedSuperclass //부모 클래스를 상속받는 자식 클래스한테 매핑 정보만 제공
@Getter
@Setter
public class BaseTimeEntity {
	
	@CreatedDate //엔티티가 생성되서 저장될떄 시간을 자동으로 저장해주는 설정
	@Column(updatable = false)//이 컬럼은 수정이 안된다는 설정
	private LocalDateTime regTime; //등록날짜
	
	@LastModifiedDate
	private LocalDateTime upDateTime; //수정날짜
}




