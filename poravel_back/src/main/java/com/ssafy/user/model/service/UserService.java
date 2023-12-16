package com.ssafy.user.model.service;

import com.ssafy.user.model.UserDto;


public interface UserService {
	UserDto loginUser(UserDto userDto) throws Exception;						// 로그인
	void joinUser(UserDto userDto) throws Exception;							// 회원가입
	int idCheck(String user_id) throws Exception;								// 아이디체크 (아이디 존재유무확인)
	UserDto userInfo(String user_id) throws Exception;							// 유저정보 조회페이지
	void saveRefreshToken(String userId, String refreshToken) throws Exception;	// 토큰 저장
	void deleteRefreshToken(String userId) throws Exception;					// 토큰 삭제
	int userInfoUpdate(String user_id, UserDto userDto) throws Exception;		// 유저정보 수정
	int AccountCancellation(String userID) throws Exception;					// 회원탈퇴
	String getRefreshToken(String user_id) throws Exception;					// 토큰 발급
	int userCheck(String user_id, UserDto userDto) throws Exception;			// 유저정보 수정 전 비밀번호 확인
	UserDto FindUserId(UserDto userDto) throws Exception;						// 아이디 찾기
	UserDto FindUserPw(UserDto userDto) throws Exception;						// 비밀번호 찾기
	UserDto UserGrade(String user_id) throws Exception;							// 유저 권한 조회
}
