package com.example.myshop.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	//로그인 화면
	@GetMapping(value = "/login") 
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
/*	쿠키, 세션 궁금하면 이거 보셈
	//SessionManager 클래스를 만들어놨음 참고
	//의존성 주입
	private final SessionManager sessionManager;
	
	//쿠키, 세션 테스트
	@PostMapping(value = "/login2") 							//세션을 저장할수있는 객체		//input창에 네임이 email이랑 같은걸 여기에 알아서 가져온다.
	public String loginMember2(HttpServletResponse response, HttpSession session, @RequestParam String email) {
		//쿠키 이용
		System.out.println("email:" + email);
										//키와 , 밸류
		Cookie idCookie = new Cookie("userCookieId", email); //쿠키에 이메일을 저장해줄 준비(쿠키생성)
		response.addCookie(idCookie); //브라우저에 쿠키를 저장하기
		
		//세션 이용
//		session.setAttribute("userSessionId2", email); //이렇게 한줄만 써도 저장은 됨,세선 객체 자체에 단순히 저장한것
		//sessionManager클래스 안에 있는 메소드 실행
		sessionManager.createSession(email, response);
		
		return "member/memberLoginForm";
	}
*/	
	
	//로그인을 실패했을때
	@GetMapping(value = "/login/error") 
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}
	
	
}

