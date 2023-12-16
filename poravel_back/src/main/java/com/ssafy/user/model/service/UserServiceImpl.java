package com.ssafy.user.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.bookmark.model.BookMarkDto;
import com.ssafy.memory.model.MemoryDto;
import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	private UserMapper userMapper;
	
	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}
	
	@Override
	public UserDto loginUser(UserDto memberDto) throws Exception {
		log.debug("loginUser call");
		return userMapper.loginUser(memberDto);
	}
	
	@Override
	public void joinUser(UserDto userDto) throws Exception {
		log.debug("joinUser call");
		userMapper.joinUser(userDto);
	}
	
	@Override
	public int idCheck(String user_id) throws Exception {			// 아이디체크 (아이디 존재유무확인)
		log.debug("idCheck call");
		return userMapper.idCheck(user_id); 
	}
	
	@Override
	public UserDto userInfo(String user_id) throws Exception {		// 유저정보 조회페이지
		log.debug("userInfo call");
		UserDto userDto = userMapper.userInfo(user_id);
		List<BookMarkDto> bookmarkDtoList = userMapper.ListBookMark(user_id);
		userDto.setBookmarkDtoList(bookmarkDtoList);
		List<MemoryDto> memoryDtoList = userMapper.ListMemoryDto(user_id);
		userDto.setMemoryDtoList(memoryDtoList);
		return userDto;
	}
	
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {		// 토큰 저장
		log.debug("saveRefreshToken call");
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}
	
	@Override
	public void deleteRefreshToken(String userId) throws Exception {		// 토큰 삭제
		log.debug("deleteRefreshToken call");
		userMapper.deleteRefreshToken(userId);
	}
	
	@Override
	public int userInfoUpdate(String user_id, UserDto userDto) throws Exception {	// 유저정보 수정
		log.debug("deleteRefreshToken call");
		userDto.setUser_id(user_id);
		return userMapper.userInfoUpdate(userDto);
	}
	
	@Override
	public int AccountCancellation(String userID) throws Exception {				// 회원 탈퇴
		log.debug("AccountCancellation call");
		return userMapper.AccountCancellation(userID);
	}
	
	@Override
	public String getRefreshToken(String user_id) throws Exception {				// 토큰 발급
		log.debug("getRefreshToken call");
		return userMapper.getRefreshToken(user_id);		
	}
	
	@Override
	public int userCheck(String user_id, UserDto userDto) throws Exception {						// 유저정보 수정 전 비밀번호 확인
		log.debug("userCheck call");
		userDto.setUser_id(user_id);
		return userMapper.userCheck(userDto);
	}
	
	@Override
	public UserDto FindUserId(UserDto userDto) throws Exception {				// 아이디찾기
		log.debug("FindUserId call");
		return userMapper.FindUserId(userDto);
	}
	
	@Override
	public UserDto FindUserPw(UserDto userDto) throws Exception {						// 비밀번호 찾기
		log.debug("FindUserPw call");
		return userMapper.FindUserPw(userDto);
		
	}
	
	@Override
	public UserDto UserGrade(String user_id) throws Exception {						// 유저 권한 조회
		log.debug("UserGrade call");
		return userMapper.UserGrade(user_id);
	}
}
