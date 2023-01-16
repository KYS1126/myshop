package com.example.myshop.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myshop.DTO.MemberFormDto;
import com.example.myshop.entity.Member;
import com.example.myshop.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor //필드로 의존성 주입하기
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	//get
	@GetMapping(value = "/new") //회원가입 화면을 보여주는 메소드
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	//post
	@PostMapping(value = "/new") //회원가입 버튼을 눌렀을떄 실행되는 메소드
							//@valid = 유효성을 체크 하고싶은 객체 앞에 붙여줘야 한다.
							//BindingResult = 유효성 검증 후에 결과를 bindingResult에 넣어준다.
	public String memberForm (@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		//bindingResult.hasErrors() 에러가 하나라도 나오면 트루가 리턴됨
		if(bindingResult.hasErrors()) {
			return "member/memberForm"; 
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);			
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		
		
		return "redirect:/";//인설트나 수정 업데이트 같은 경우는 리다이렉트 사용하자 (/ : 로컬 호스트로 보내준다. 메인)
	
	
	}
	
}
