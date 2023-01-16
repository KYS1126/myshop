package com.example.myshop.DTO;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

//회원가입으로부터 넘어오는 가입정보를 담을 DTO
@Getter
@Setter
public class MemberFormDto {
	
	//null체크 및 문자열의 길이가 0이거나 빈문자열 검사
	@NotBlank(message = "이름은 필수 입력값 입니다.")
	private String name;
	
	//null체크 및 문자열의 길이 0인지 검사
	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자이상 16자 이하로 작성해주세요")
	private String password;
	
	@NotEmpty(message = "주소는 필수 입력 값입니다.")
	private String address;

}
