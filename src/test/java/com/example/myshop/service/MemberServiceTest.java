package com.example.myshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.myshop.DTO.MemberFormDto;
import com.example.myshop.constant.Role;
import com.example.myshop.entity.Member;

@SpringBootTest
@Transactional //잘못되면 롤백 해주는 애 (테스트 코드에서는 데이터가 저장 안되게 막는다)
@TestPropertySource(locations="classpath:application-test.properties") // db연결하기
class MemberServiceTest {
	@Autowired
	MemberService memberService; //의존성 주입
	
	@Autowired
	PasswordEncoder passwordEncoder; //의존성 주입

	public Member createMember() { //테스트 용이기 때문에 데이터를 가라로 넣어준다.
		MemberFormDto member = new MemberFormDto(); //위에 있는 멤커 클래스 생성
		member.setName("홍길동");
		member.setEmail("test@email.com");
		member.setAddress("서울시 마포구 합장동");
		member.setPassword("1234");
		
		return Member.createMember(member, passwordEncoder);
	}
	
	@DisplayName("회원가입 테스트")
	public void saveMemberTest() {
		Member member = createMember(); //크리에이트 멤버 메소드 실행
		Member savedMember = memberService.saveMember(member); //멤버서비스에서 세이브 멤버 실행 //여기서 insert가 된다.
		
		//(내가 가라로 넣은 값이랑, DB에 insert된 값이랑 같은지 비교함)
		assertEquals(member.getEmail(),savedMember.getEmail()); //테스트 코드를 사용할때 값을 비교하는애
		assertEquals(member.getName(),savedMember.getName()); //테스트 코드를 사용할때 값을 비교하는애
		assertEquals(member.getAddress(),savedMember.getAddress()); //테스트 코드를 사용할때 값을 비교하는애
		assertEquals(member.getPassword(),savedMember.getPassword()); //테스트 코드를 사용할때 값을 비교하는애
		assertEquals(member.getRole(),savedMember.getRole()); //테스트 코드를 사용할때 값을 비교하는애
	}
	
	@Test
	@DisplayName("중복 회원 가입 테스트")
	public void saveDuplicateMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		
		memberService.saveMember(member1);
		
		//예외처리 테스트
		//이메일 중복체크에서 예외를 받고, e에 넣어준다.
		Throwable e = assertThrows(IllegalStateException.class,()->{
			memberService.saveMember(member2); //insert 중에 에러가 발생하면 여기서 걸린다.
		});
		
		//e => 예외 처리된 객체가 들어있음
		assertEquals("이미 가입된 회원입니다.",e.getMessage());
	}
	
	
	
	
}


