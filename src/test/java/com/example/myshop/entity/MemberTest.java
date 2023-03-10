package com.example.myshop.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.example.myshop.repositoty.MemberRepository;

@SpringBootTest
@Transactional //롤백 해주는 애
@TestPropertySource(locations="classpath:application-test.properties")
class MemberTest {

	@Autowired
	MemberRepository memberRepository;
	
	@PersistenceContext
	EntityManager em;
	
	@Test
	@DisplayName("auditing 테스트")
	@WithMockUser(username = "gildong", roles = "USER") //username = "gildong", roles = "USER"의 유저가 로그인 상태라고 가정한다.
	public void  auditingTest() {
		Member newMember = new Member();
		memberRepository.save(newMember);
		
		em.flush();
		em.clear();
		
		Member member = memberRepository.findById(newMember.getId())
						.orElseThrow(EntityNotFoundException::new);
		
		System.out.println("등록시간:" + member.getRegTime());
		System.out.println("수정시간:" + member.getUpDateTime());
		System.out.println("등록한 사람:" + member.getCreatedBy());
		System.out.println("수정한 사람:" + member.getModifiedBy());
		
						
	}
	

}
