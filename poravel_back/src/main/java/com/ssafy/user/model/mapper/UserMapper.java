package com.ssafy.user.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bookmark.model.BookMarkDto;
import com.ssafy.memory.model.MemoryDto;
import com.ssafy.user.model.UserDto;

@Mapper
public interface UserMapper {
	UserDto loginUser(UserDto userDto) throws SQLException;		// 로그인
	void joinUser(UserDto userDto) throws SQLException;			// 회원가입
	int idCheck(String user_id) throws SQLException;			// 아이디체크 (아이디 존재유무확인)
	UserDto userInfo(String user_id) throws SQLException;		// 유저정보 조회
	void saveRefreshToken(Map<String, String> map) throws SQLException;	// 토큰 저장
	void deleteRefreshToken(String userId) throws SQLException;	// 토큰 삭제
	int userInfoUpdate(UserDto userDto) throws SQLException;	// 유저정보 수정
	int AccountCancellation(String userID) throws SQLException;	// 회원탈퇴
	String getRefreshToken(String user_id) throws SQLException;	// 토큰 발급
	int userCheck(UserDto userDto) throws SQLException;			// 유저정보 수정 전 비밀번호 확인
	UserDto FindUserId(UserDto userDto) throws SQLException;	// 아이디 찾기
	UserDto FindUserPw(UserDto userDto) throws SQLException;	// 비밀번호 찾기
	UserDto UserGrade(String user_id) throws SQLException;		// 유저 권한 조회
	List<BookMarkDto> ListBookMark(String user_id) throws SQLException;	// 한 유저의 북마크 조회
	List<MemoryDto> ListMemoryDto(String user_id) throws SQLException;	// 한 유저의 작성한 메모리 조회
}
