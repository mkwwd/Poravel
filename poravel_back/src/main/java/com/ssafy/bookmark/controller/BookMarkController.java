package com.ssafy.bookmark.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bookmark.model.BookMarkDto;
import com.ssafy.bookmark.model.service.BookMarkService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bookmark")
@Slf4j
@Api(value = "/bookmark", tags= {"BookMarkAPI"}, description="북마크에 관한 RESTfulAPI")
public class BookMarkController {
	
	@Autowired
	private BookMarkService bookmarkService;
	
	@Autowired
	private JWTUtil jwtUtil;

	// 북마크 추가하기
	@PostMapping
	public ResponseEntity<?> AddBookMark(@RequestBody BookMarkDto bookmarkDto, HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("AddBookMark BookMarkDto : {}", bookmarkDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			String user_id = jwtUtil.getUserId(accessToken);
			
			try {
				bookmarkService.AddBookMark(user_id, bookmarkDto);
				resultMap.put("message", "북마크 추가 성공");
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.debug("북마크 추가 에러 발생 : {}", e);
				resultMap.put("error", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.debug("사용 불가능한 토큰");
			resultMap.put("message", "사용자 인증 정보가 유효하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 북마크 삭제하기
	@DeleteMapping("/{bookmark_pk}")
	public ResponseEntity<?> DeleteBookMark(@PathVariable("bookmark_pk") int bookmark_pk, HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("DeleteBookMark bookmark_pk : {}", bookmark_pk);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			
			try {
				bookmarkService.DeleteBookMark(bookmark_pk);
				resultMap.put("message", "북마크 삭제 성공");
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.debug("북마크 삭제 에러 발생 : {}", e);
				resultMap.put("error", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.debug("사용 불가능한 토큰");
			resultMap.put("message", "사용자 인증 정보가 유효하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
}
