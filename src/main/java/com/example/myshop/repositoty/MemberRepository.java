package com.example.myshop.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myshop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findByEmail(String email); //이메일로 검색하는 쿼리메소드(회원가입시 중복 회원이 있는지 검사하기 위해 만듬)
	
	
	
}
