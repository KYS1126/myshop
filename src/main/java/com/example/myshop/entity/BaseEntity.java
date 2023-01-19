package com.example.myshop.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해 사용하는 어노테이션
@MappedSuperclass //부모 클래스를 상속받는 자식 클래스한테 매핑 정보만 제공
@Getter
public class BaseEntity extends BaseTimeEntity{
	
	@CreatedBy
	@Column(updatable = false)//이 컬럼은 수정이 안된다는 설정
	private String createBy; //등록자
	
	
	@LastModifiedBy
	private String modifiedBy; //수정자
	
	
	
}
