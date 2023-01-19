package com.example.myshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//업로드한 파일을 읽어올 경로를 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	
	@Value("${uploadPath}") //프로퍼티의 값을 읽어온다.
	String uploadPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//웹 브라우저에 입력하는 url /images로 시작하는 uploadPath에 설정한 폴더를 기준으로 파일을 읽어온다,
		registry.addResourceHandler("/images/**") 
				.addResourceLocations(uploadPath);
	}

	
	

}
