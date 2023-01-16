package com.example.myshop.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myshop.entity.Member;
import com.example.myshop.repositoty.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service //서비스 클래스의 역할
@Transactional //현재 클래스에서 만약에 밑에 로직(메소드)을 실행하다가 에러가 나면 이전 상태로 되돌려준다.
@RequiredArgsConstructor //의존성 주입할때 final을 써야하면 얘를 써줘야함
//1.생성자로 의존성 주입하는법
//2.setter로 의존성 주입
//3.필드로(final) 생성자 주입
public class MemberService implements UserDetailsService{ //로그인 시 request에서 넘어온 사용자 정보를 받음
	private final MemberRepository memberRepository; //의존성 주입
	
	//매개변수로 주면 이메일의 정보를 알아서 받아온다.
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email);
		
		if(member == null) {
			throw new UsernameNotFoundException(email);
		}
		//userDetails의 객체를 반환
		return User.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
	}
	
	public Member saveMember(Member member) { //db에 세이브 해주는애
		validateDuplicateMember(member); //이메일 중복체크
		return memberRepository.save(member); //member 테이블에 insert
	}
	
	//이메일 중복체크 메소드
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail()); //select문
		if (findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}


	
	
	
}
