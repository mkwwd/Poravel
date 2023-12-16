package com.ssafy.map.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoDto;
import com.ssafy.map.model.service.MapService;
import com.ssafy.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/map")
@Slf4j
public class MapController {

	@Autowired
	private MapService mapService;
	@Autowired
	private JWTUtil jwtUtil;
	
	@GetMapping("/sido")
	public ResponseEntity<?> sidoList() {
		log.debug("sidoList Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<SidoDto> sidoDtoList = mapService.sidoList();
			status = HttpStatus.OK;
			resultMap.put("sidoList", sidoDtoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/category")
	public ResponseEntity<?> categoryList() {
		log.debug("categoryList Controller");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<Map<String, Object>> categoryList = mapService.categoryList();
			status = HttpStatus.OK;
			resultMap.put("categoryList", categoryList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping
	public ResponseEntity<?> attractionList(@RequestParam Map<String, String> map) {
		log.debug("attractionList Controller map : {}", map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<AttractionDto> attractionList = mapService.attractionList(map);
			status = HttpStatus.OK;
			resultMap.put("attractionList", attractionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/{content_id}")
	public ResponseEntity<?> attractionDetail(@PathVariable("content_id") int content_id) {
		log.debug("attractionList Controller content_id : {}", content_id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			AttractionDto attractionDto = mapService.attractionDetail(content_id);
			status = HttpStatus.OK;
			resultMap.put("attractionDetail", attractionDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
