package com.example.myshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.myshop.service.MemberService;

@Configuration //스프링에서 설정 클래스로 사용하겠다.
@EnableWebSecurity //springSecurityFilterChain이 자동으로 포함됨(필터추가)
public class SecurityConfig{

	@Autowired
	MemberService memberService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//로그인에 대한 설정
		http.formLogin()
		.loginPage("/members/login")//로그인 페이지 url을 지정한다.
		.defaultSuccessUrl("/")  //로그인 성공 시 이동할 페이지
		.usernameParameter("email") //로그인 시 사용할 파라메터 이름
		.failureUrl("/members/login/error") //로그인 실패 시 이동할 url 지정
		.and() //걍 규칙
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 url 지정
		.logoutSuccessUrl("/"); //로그아웃 성공시 이동할 url 지정
		
		return http.build();
		
	}
	
	@Bean //스프링에서 쓰이는 객체 (객체를 리턴해주는 애)
	public PasswordEncoder passwordEncoder() { //비밀번호 암호화를 위해서 사용하는 빈(bean)
		return new BCryptPasswordEncoder();    //암호화를 사용하기위한 함수
	}
	
	
}
