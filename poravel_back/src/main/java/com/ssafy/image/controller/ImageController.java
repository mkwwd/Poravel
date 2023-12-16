package com.ssafy.image.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.image.model.ImageDto;
import com.ssafy.image.model.service.ImageService;
import com.ssafy.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	// 이미지 보여주기
	@GetMapping(value="/{type}/{type_pk}")
	public ResponseEntity<?> ViewImage(@PathVariable("type") String type, @PathVariable("type_pk") String type_pk) {
		log.debug("ViewImage type : {} / type_id : {}", type, type_pk);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		Resource resource = null;
		HttpHeaders header = new HttpHeaders();
		try {
			ImageDto imageDto = imageService.ViewImage(type, type_pk);
			System.out.println(imageDto);
			String path = System.getProperty("user.dir")+imageDto.getSave_folder();
			String fileName = imageDto.getSaved_name();
			resource = new FileSystemResource(path + File.separator + fileName);
			System.out.println(resource);
			if(!resource.exists()) return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
			Path filePath = null;
			filePath = Paths.get(path + File.separator + type + File.separator + fileName);
			header.add("Content-Type", Files.probeContentType(filePath));
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.debug("이미지 조회 에러 발생 : {}", e);
			resultMap.put("message", "이미지 조회 에러 발생");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Resource>(resource, header, status);
		
	}
	
	// 이미지 입력하기
	@PostMapping
	public ResponseEntity<?> uploadImage(
			@RequestParam("image") MultipartFile uploadImage,
			@RequestParam("type") String type,
			HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		log.debug("MultipartFile.isEmpty : {}, type : {}", uploadImage.isEmpty(), type);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;

		String user_id = jwtUtil.getUserId(accessToken);
		if(jwtUtil.checkToken(accessToken)) {
			log.debug("사용 가능한 토큰");
			try {
				boolean success = imageService.uploadImage(type, user_id, uploadImage);
				if(success==true) {
					resultMap.put("messgae", "이미지 업로드 성공");
					status = HttpStatus.OK;
				}
				else if(success==false) {
					resultMap.put("messgae", "이미지가 업로드되어있지 않습니다.");
					status = HttpStatus.OK;
				}
			} catch (Exception e) {
				log.debug("이미지 업로드 실패 에러 발생 : {}", e);
				resultMap.put("message", "이미지 업로드 실패 에러 발생");
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
