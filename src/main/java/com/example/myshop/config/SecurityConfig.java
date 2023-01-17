package com.example.myshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
		
		//페이지의 접근에 관한 설정
		
		
		http.authorizeRequests()
	    .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
	    .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll() //모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정
	    .mvcMatchers("/admin/**").hasRole("ADMIN") // '/admin' 으로 시작하는 경로는 계정이 ADMIN role일 경우에만 접근 가능하도록 설정
	    .anyRequest().authenticated(); //그 외에 페이지는 모두 로그인(인증)을 받아야 한다.
		
		
		
		//인증되지 않은 사용자가 리소스(페이지, 이미지 등...)에 접근했을때 설정
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());//CustomAuthenticationEntryPoint 클래스에서 commence 메소드를 실행한다.
		
		
		return http.build();
		
		
	}
	
	@Bean //스프링에서 쓰이는 객체 (객체를 리턴해주는 애)
	public PasswordEncoder passwordEncoder() { //비밀번호 암호화를 위해서 사용하는 빈(bean)
		return new BCryptPasswordEncoder();    //암호화를 사용하기위한 함수
	}
	
	
}
