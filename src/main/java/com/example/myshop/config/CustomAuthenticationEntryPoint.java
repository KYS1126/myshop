package com.example.myshop.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
																
//인증되지 않은 사용자가 리소스를 요청할 경우 어떻게 처리할지에 대한 클래스
														//인증할떄 에러가 발생하면 그 에러를 핸들링 해주는 애
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); //401에러
			
		
	} 
	
	
}
