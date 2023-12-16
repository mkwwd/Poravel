package com.ssafy.memory.controller;

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
import com.ssafy.user.model.service.UserService;
import com.ssafy.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/memory")
@Slf4j
public class MemoryController {
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private MemoryService memoryService;
	@Autowired
	private JWTUtil jwtUtil;
	
	public MemoryController(MemoryService memoryService, JWTUtil jwtUtil) {
		super();
		this.memoryService = memoryService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> memoryList(@RequestParam Map<String, String> map) {		// 메모리 리스트
		log.debug("memoryList Controller {}" , map);
		
		Set set = map.keySet();
		if(!set.contains("type")) map.put("type", "memory_date");
		if(!set.contains("order")) map.put("order", "desc");
		if(!set.contains("search")) map.put("search", "");
		
		System.out.println(map);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<MemoryDto> memoryList = memoryService.memoryList(map);
			resultMap.put("memoryList", memoryList);
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorMessage", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@GetMapping("/best")
	public ResponseEntity<?> BestMemory() {		// 베스트 메모리 (GET: /best)
		log.debug("BestMemory Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<MemoryDto> BestMemory = memoryService.BestMemory();
			resultMap.put("BestMemory", BestMemory);
			status = HttpStatus.OK;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorMessage", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@PostMapping
	public ResponseEntity<?> writeMemory(		// 메모리 작성 (POST)
			@RequestParam("image") MultipartFile file,
			@RequestParam("user_name") String user_name,
			@RequestParam("location") String location,
			@RequestParam("comment") String comment,
			@RequestParam("memory_date") String memory_date,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			HttpServletRequest request) {		
		log.debug("writeMemory Controller imageisEmpty : {} / user_name : {} / location : {} / comment : {} / memory_date : {}", file.isEmpty(), user_name, location, comment, memory_date);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtUtil.getUserId(accessToken);
		
		MemoryDto memoryDto = new MemoryDto();
		memoryDto.setUser_name(user_name);
		memoryDto.setLocation(location);
		memoryDto.setComment(comment);
		memoryDto.setMemory_date(memory_date);
		memoryDto.setLatitude(latitude);
		memoryDto.setLongitude(longitude);
		
		if (jwtUtil.checkToken(accessToken)) {
			try {
				imageService.uploadImage("memory", user_id, file);
				
				memoryService.writeMemory(memoryDto, user_id);
				resultMap.put("message", "메모리 작성 성공");
				status = HttpStatus.OK;
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("errorMessage", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", "잘못된 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@PutMapping("/{memory_pk}")
	public ResponseEntity<?> UpdateMemory(		// 메모리 수정 (PUT : /{pk}) 본인만 수정 가능
			@PathVariable("memory_pk") String memory_pk,
			@RequestParam("image") MultipartFile uploadImage,
			@RequestParam("user_name") String user_name,
			@RequestParam("location") String location,
			@RequestParam("comment") String comment,
			@RequestParam("memory_date") String memory_date,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			HttpServletRequest request) {		// 메모리 작성 (POST)
		
		log.debug("UpdateMemory Controller memory_pk : {} / imageisEmpty : {} / user_name : {} / location : {} / commnet : {} / memory_date : {}", memory_pk, uploadImage.isEmpty(), user_name, location, comment, memory_date);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtUtil.getUserId(accessToken);
		MemoryDto memoryDto = new MemoryDto();
		memoryDto.setUser_name(user_name);
		memoryDto.setLocation(location);
		memoryDto.setComment(comment);
		memoryDto.setMemory_date(memory_date);
		memoryDto.setLatitude(latitude);
		memoryDto.setLongitude(longitude);
		
		if (jwtUtil.checkToken(accessToken)) {
			try {
				imageService.updateImage(memory_pk, uploadImage);
				int count = memoryService.UpdateMemory(memoryDto, user_id, Integer.parseInt(memory_pk));
				if(count==0) {
					resultMap.put("message", "메모리 수정 실패 (아이디 또는 PK번호 확인)");
					status = HttpStatus.ACCEPTED;
				}
				else if(count > 0) {
					resultMap.put("message", "메모리 수정 성공");
					status = HttpStatus.OK;
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("errorMessage", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", "잘못된 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@DeleteMapping("/{memory_pk}")
	public ResponseEntity<?> DeleteMemory(		// 메모리 삭제 (DELETE : /{pk})		
			@PathVariable("memory_pk") int memory_pk,
			HttpServletRequest request) {		// 메모리 작성 (POST)
		
		log.debug("DeleteMemory Controller memory_pk : {}", memory_pk);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtUtil.getUserId(accessToken);
		
		if (jwtUtil.checkToken(accessToken)) {
			try {
				int count = memoryService.DeleteMemory(user_id, memory_pk);
				if(count==0) {
					resultMap.put("message", "메모리 삭제 실패 (아이디 또는 PK번호 확인)");
					status = HttpStatus.ACCEPTED;
				}
				else if(count > 0) {
					resultMap.put("message", "메모리 삭제 성공");
					status = HttpStatus.OK;
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("errorMessage", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			resultMap.put("message", "잘못된 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/{memory_pk}")
	public ResponseEntity<?> detailMemory(@PathVariable("memory_pk") int memory_pk) {	// 메모리 조회 (GET : /{pk}) => 조회수 1 증가
		log.debug("detailMemory Controller memory_pk : {}", memory_pk);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			MemoryDto memoryDto = memoryService.detailMemory(memory_pk);
			memoryService.increaseHit(memory_pk);
			resultMap.put("memoryDto", memoryDto);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorMessage", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
