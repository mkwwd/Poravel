package com.ssafy.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.model.AnswerDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.user.model.service.UserService;
import com.ssafy.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {
	
	private BoardService boardService;
	private UserService userService;
	private JWTUtil jwtUtil;
	
	public BoardController(BoardService boardService, UserService userService, JWTUtil jwtUtil) {
		super();
		this.boardService = boardService;
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	// 리스트 불러오기
	@GetMapping(value="/{article_type}")
	public ResponseEntity<?> ListArticle(
			@PathVariable("article_type") String article_type,
			@RequestParam Map<String, String> map
			) {	
		log.debug("ListArticle [article_type : " + article_type + " / pgno : " + map.get("pgno") + " / key :" + map.get("key") + " / word : " + map.get("word") + " ]");
		try {
			map.put("article_type", article_type);
			BoardListDto boardListDto = boardService.ListArticle(map);
			return new ResponseEntity<BoardListDto> (boardListDto, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 게시판 쓰기
	@PostMapping("/{article_type}")
	public ResponseEntity<?> writeArticle(
			@PathVariable("article_type") String article_type,
			@RequestBody BoardDto boardDto,
			HttpServletRequest request) {
		log.debug("writeArticle : " + boardDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		String accessToken = request.getHeader("Authorization");
		if (jwtUtil.checkToken(accessToken)) {		// 토큰 인증성공시
			// 권한 확인 admin이면 모두가능, user이면 공지사항만 가능
			String user_id = jwtUtil.getUserId(accessToken);
			try {
				String user_grade = userService.UserGrade(user_id).getUser_grade();
				if(user_grade.equals("admin"))	{
					boardService.writeArticle(user_id, article_type, boardDto);
					status=HttpStatus.OK;
					resultMap.put("message", "게시글 등록 성공");
				}
				else if(user_grade.equals("user")) {
					if(article_type.equals("notice")) {
						status=HttpStatus.OK;
						resultMap.put("message", "게시글 등록 권한이 없습니다.");
					} else if(article_type.equals("qna")) {
						boardService.writeArticle(user_id, article_type, boardDto);
						status=HttpStatus.OK;
						resultMap.put("message", "게시글 등록 성공");
					}
				}
			} catch (Exception e) {
				log.error("사용자 등급 조회 실패 : {}", e);
				resultMap.put("message", "사용자 등급 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else { 
			resultMap.put("message", "토큰이 일치하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 게시판 상세조회
	@GetMapping(value="/{article_type}/{article_pk}")
	public ResponseEntity<?> detailArticle(
			@PathVariable("article_type") String article_type, 
			@PathVariable("article_pk") String article_pk) {
		
		log.debug("[detailArticle] article_type : {} / article_pk : {}" + article_type, article_pk);
		try {
			BoardDto boardDto = boardService.detailArticle(article_pk);
			System.out.println(boardDto.getContent());
			return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	// 게시판 수정
	@PutMapping
	public ResponseEntity<?> updateArticle(@RequestBody BoardDto boardDto, HttpServletRequest request) throws Exception {
		log.debug("updateArticle : " + boardDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		String accessToken = request.getHeader("Authorization");
		System.out.println(jwtUtil.getUserId(accessToken));
		if (jwtUtil.checkToken(accessToken) && jwtUtil.getUserId(accessToken).equals(boardDto.getUser_id())) {		// 토큰 인증성공시
			try {
				boardService.updateArticle(boardDto);
				status = HttpStatus.OK;
				resultMap.put("message", "게시판 수정 성공");
			} catch (Exception e) {
				log.debug("게시판 수정 에러 : {}", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				resultMap.put("message", "게시판 수정 에러");
			}
		} else if(!jwtUtil.checkToken(accessToken)) {	// 토큰 인증 실패시
			System.out.println("토큰인증실패");
			resultMap.put("message", "토큰이 일치하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		} else if(!jwtUtil.getUserId(accessToken).equals(boardDto.getUser_id())) { // 토큰과 작성자가 일치하지 않을 시
			resultMap.put("message", "수정권한이 없습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 게시판 삭제
	@DeleteMapping("/{article_pk}")
	public ResponseEntity<?> deleteArticle(@PathVariable String article_pk, HttpServletRequest request) {
		log.debug("deleteArticle article_pk : " + article_pk);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String accessToken = request.getHeader("Authorization");
		
		if (jwtUtil.checkToken(accessToken)) {
			String user_id = jwtUtil.getUserId(accessToken);
			try {
				String user_grade = userService.UserGrade(user_id).getUser_grade();		// 유저 등급 조회
				BoardDto resultBoardDto = boardService.detailArticle(article_pk);
				String resultUser_id = resultBoardDto.getUser_id();
				String article_type = resultBoardDto.getArticle_type();
				// 글의 article_type이 notice이면 유저등급이 admin일 때만 삭제가능.
				System.out.println(user_grade);
				if(article_type.equals("notice") && user_grade.equals("admin")) {
					int count = boardService.deleteArticle(article_pk);
					status = HttpStatus.OK;
					resultMap.put("message", "게시판 삭제 성공");
				}
				// 글의 article_type이 qna이면 유저와 이름이 같을때거나, 유저등급이 admin일 때에만 삭제가능.
				else if(article_type.equals("qna") && (user_id.equals(resultUser_id) || user_grade.equals("admin"))) {
					int count = boardService.deleteArticle(article_pk);
					status = HttpStatus.OK;
					resultMap.put("message", "게시판 삭제 성공");
				}
				else {
					resultMap.put("message", "권한이 없습니다.");
					status = HttpStatus.OK;
				}
				
			} catch (Exception e) {
				log.debug("게시판 삭제 에러 : {}", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				resultMap.put("message", "게시판 삭제 에러");
			}
		}
		else {
			System.out.println("토큰인증실패");
			resultMap.put("message", "토큰이 일치하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 댓글 쓰기	(관리자)
	@PostMapping(value="/qna/{article_pk}")
	public ResponseEntity<?> writeAnswer(
			@PathVariable("article_pk") int article_pk,
			@RequestBody AnswerDto answerDto, 
			HttpServletRequest request) { 
		
		answerDto.setArticle_pk(article_pk);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtUtil.getUserId(accessToken);
		answerDto.setUser_id(user_id);
		log.debug("writeAnswer : " + answerDto);
		
		if (jwtUtil.checkToken(accessToken)) {
			try {
				String user_grade = userService.UserGrade(user_id).getUser_grade();
				if(user_grade.equals("admin"))	{
					boardService.writeAnswer(answerDto);
					status=HttpStatus.OK;
					resultMap.put("message", "댓글 등록 성공");
				} else {
					status=HttpStatus.OK;
					resultMap.put("message", "댓글 등록 권한이 없습니다.");
				}
			} catch (Exception e) {
				log.error("사용자 등급 조회 실패 : {}", e);
				resultMap.put("message", "사용자 등급 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", "토큰이 일치하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	// 댓글 수정	(관리자)
	@PutMapping(value="/qna")
	public ResponseEntity<?> updateAnswer(
			@RequestBody AnswerDto answerDto,
			HttpServletRequest request) { 
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtUtil.getUserId(accessToken);
		log.debug("updateAnswer : " + answerDto);
		
		if (jwtUtil.checkToken(accessToken)) {
			try {
				String user_grade = userService.UserGrade(user_id).getUser_grade();
				if(user_grade.equals("admin"))	{
					boardService.updateAnswer(answerDto);
					status=HttpStatus.OK;
					resultMap.put("message", "댓글 수정 성공");
				} else {
					status=HttpStatus.OK;
					resultMap.put("message", "댓글 수정 권한이 없습니다.");
				}
			} catch (Exception e) {
				log.error("사용자 등급 조회 실패 : {}", e);
				resultMap.put("message", "사용자 등급 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", "토큰이 일치하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 댓글 삭제	(관리자)
	@DeleteMapping(value="/qna/{reply_pk}")
	public ResponseEntity<?> deleteAnswer(
			@PathVariable("reply_pk") int reply_pk,
			HttpServletRequest request) { 
		
		log.debug("deleteAnswer : " + reply_pk);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtUtil.getUserId(accessToken);
		
		if (jwtUtil.checkToken(accessToken)) {
			try {
				String user_grade = userService.UserGrade(user_id).getUser_grade();
				if(user_grade.equals("admin"))	{
					boardService.deleteAnswer(reply_pk);
					status=HttpStatus.OK;
					resultMap.put("message", "댓글 삭제 성공");
				} else {
					status=HttpStatus.OK;
					resultMap.put("message", "댓글 삭제 권한이 없습니다.");
				}
			} catch (Exception e) {
				log.error("사용자 등급 조회 실패 : {}", e);
				resultMap.put("message", "사용자 등급 조회 실패");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", "토큰이 일치하지 않습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
