package com.ssafy.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.model.AnswerDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.image.model.service.ImageService;
import com.ssafy.memory.model.MemoryDto;
import com.ssafy.memory.model.service.MemoryService;
import com.ssafy.review.model.ReviewDto;
import com.ssafy.review.model.service.ReviewService;
import com.ssafy.user.model.service.UserService;
import com.ssafy.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping
	public ResponseEntity<?> WriteReview(@RequestBody ReviewDto reviewDto, HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("WriteReview Controller ReviewDto : {}" , reviewDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if(jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			String user_id=jwtUtil.getUserId(accessToken);
			try {
				reviewService.WriteReview(user_id, reviewDto);
				resultMap.put("message", "리뷰 등록 성공");
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.debug("리뷰 등록 에러 발생 : {}", e);
				resultMap.put("error", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else {
			log.debug("사용 불가능한 토큰");
			status=HttpStatus.UNAUTHORIZED;
			resultMap.put("message", "사용 불가능한 토큰");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping("/{review_pk}")
	public ResponseEntity<?> DeleteReview(@PathVariable("review_pk") int review_pk, HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("DeleteReview Controller review_pk : {}" , review_pk);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if(jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			String user_id=jwtUtil.getUserId(accessToken);
			try {
				reviewService.DeleteReview(review_pk, user_id);
				resultMap.put("message", "리뷰 삭제 성공");
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.debug("리뷰 삭제 에러 발생 : {}", e);
				resultMap.put("error", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else {
			log.debug("사용 불가능한 토큰");
			status=HttpStatus.UNAUTHORIZED;
			resultMap.put("message", "사용 불가능한 토큰");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}


