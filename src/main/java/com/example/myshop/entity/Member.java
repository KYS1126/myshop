package com.example.myshop.entity;


import javax.persistence.*;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.myshop.DTO.MemberFormDto;
import com.example.myshop.constant.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Member") //테이블명 설정 , 설정을 따로 안하면 클래스 명으로 자동으로 입력됨
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO) //p키 생성 전략, p키를 자동으로 만들어준다
	private Long id;
	
	private String name;
	
	@Column(unique = true) //중복되지않게 컬럼을 유니크로 만들어준다.
	private String email;
	
	private String password;
	
	private String address;
	
	@Enumerated(EnumType.STRING) //이넘 클래스에 있는 값 자체를 넣어준다.
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member(); //위에 있는 멤커 클래스 생성
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setAddress(memberFormDto.getAddress());
		
		//패스워드는 암호화가 되있기 떄문에 한번 처리해줘야 한다. (비밀번호 인코딩)
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		member.setRole(Role.USER);
		
		return member;
	}
}
