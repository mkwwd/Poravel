package com.ssafy.map.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoDto;
import com.ssafy.map.model.mapper.MapMapper;
import com.ssafy.memory.model.MemoryDto;
import com.ssafy.memory.model.mapper.MemoryMapper;
import com.ssafy.review.model.ReviewDto;
import com.ssafy.review.model.mapper.ReviewMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MapServiceImpl implements MapService {
	
	@Autowired
	private MapMapper mapMapper;
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	@Autowired
	private MemoryMapper memoryMapper;

	public List<SidoDto> sidoList() throws Exception {
		log.debug("sidoList call");
		return mapMapper.sidoList();
	}
	
	public List<Map<String, Object>> categoryList() throws Exception {
		log.debug("categoryList call");		
		return mapMapper.categoryList();
	}
	
	public List<AttractionDto> attractionList(Map<String, String> map) throws Exception {
		log.debug("attractionCategoryList call");
		Set set = map.keySet();
		
		if(set.contains("sido_code") && set.contains("category_pk")) {
			AttractionDto attractionDto = new AttractionDto();
			attractionDto.setSido_code(Integer.parseInt(map.get("sido_code")));
			attractionDto.setCategory_pk(Integer.parseInt(map.get("category_pk")));
			return mapMapper.attractionCategoryList(attractionDto);
		} else if(set.contains("title")) {
			return mapMapper.attractionSearchList(map.get("title"));
		}
		return null;
	}
	
	public AttractionDto attractionDetail(int content_id) throws Exception {
		log.debug("AttractionDetail call");
		AttractionDto attractionDto = mapMapper.attractionDetail(content_id); 
		attractionDto.setReviewDtoList(reviewMapper.ListReview(content_id));
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "memory_date");
		map.put("order", "desc");
		map.put("search", attractionDto.getTitle());
		attractionDto.setMemoryDtoList(memoryMapper.memoryList(map));
		return attractionDto;
	}
	
}
