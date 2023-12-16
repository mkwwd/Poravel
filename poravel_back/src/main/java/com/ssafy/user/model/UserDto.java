package com.ssafy.user.model;

import java.util.List;

import com.ssafy.bookmark.model.BookMarkDto;
import com.ssafy.memory.model.MemoryDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "MemberDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class UserDto {
	@ApiModelProperty(value = "아이디")
	private String user_id;
	@ApiModelProperty(value = "비밀번호")
	private String user_password;
	@ApiModelProperty(value = "이름")
	private String user_name;
	@ApiModelProperty(value = "주소")
	private String address;
	@ApiModelProperty(value = "상세주소")
	private String address_detail;
	@ApiModelProperty(value = "우편번호")
	private String zip_code;
	@ApiModelProperty(value = "이메일아이디")
	private String email_id;
	@ApiModelProperty(value = "이메일도메인")
	private String email_domain;
	@ApiModelProperty(value = "핸드폰 번호")
	private String phone_num;
	@ApiModelProperty(value = "유저 등급")
	private String user_grade;
	@ApiModelProperty(value = "가입 날짜")
	private String join_date;
	@ApiModelProperty(value = "소셜 로그인 타입")
	private String social_type;
	@ApiModelProperty(value = "소셜 로그인 아이디")
	private String social_id;
	@ApiModelProperty(value = "소셜 로그인 가입 날짜")
	private String social_join_date;
	@ApiModelProperty(value = "토큰")
	private String token;
	@ApiModelProperty(value = "북마크 리스트")
	private List<BookMarkDto> bookmarkDtoList;
	@ApiModelProperty(value = "메모리 리스트")
	private List<MemoryDto> memoryDtoList;
}
