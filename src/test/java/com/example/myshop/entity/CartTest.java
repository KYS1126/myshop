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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.example.myshop.DTO.MemberFormDto;
import com.example.myshop.repositoty.CartRepository;
import com.example.myshop.repositoty.MemberRepository;
import com.example.myshop.service.MemberService;


@SpringBootTest
@Transactional //롤백 해주는 애
@TestPropertySource(locations="classpath:application-test.properties")
class CartTest {

	//의존성 주입
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//영속성 컨텍스트는 엔티티 매니저에 저장이 된다.
	@PersistenceContext //엔티티 메니저 //영속성 컨텍스트를 사용하기 위해 선언
	EntityManager em;
	
	public Member createMember() { //테스트 용이기 때문에 데이터를 가라로 넣어준다.
		MemberFormDto member = new MemberFormDto(); //위에 있는 멤커 클래스 생성
		member.setName("홍길동");
		member.setEmail("test@email.com");
		member.setAddress("서울시 마포구 합장동");
		member.setPassword("1234");
		
		return Member.createMember(member, passwordEncoder);
	}
	
	@Test
	@DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
	public void findCartAndMemberTest() {
		Member member = createMember();
		memberRepository.save(member); //영속성 컨텍스트에 일단 저장 해놓는것, 데이터 베이스에 반영이 아니다.
		
		Cart cart = new Cart();
		cart.setMember(member); //카트 클래스에 멤버 객체를 넣는다.
		cartRepository.save(cart); //영속성 컨텍스트에 일단 저장 해놓는것, 데이터 베이스에 반영이 아니다.
		
		//엔티티 생명주기
		em.flush(); //트렌젝션이 끝날때 데이터 베이스에 반영
		em.clear(); //영속성 컨텍스트에서 엔티티를 비워준다. //해주는 이유는 실제 데이터 베이스에서 장바구니 엔티티를 가져올때 회원 엔티티도
		//같이 가지고 오는지 보기위해
		
										//리턴값이 옵셔널
		Cart savedCart = cartRepository.findById(cart.getId())
				.orElseThrow(EntityNotFoundException::new); //에러 처리를 간단하게 해주는 친구
		
		//비교 해주는 명령어
		assertEquals(savedCart.getMember().getId(), member.getId());
		
		
		
	}
	
}
