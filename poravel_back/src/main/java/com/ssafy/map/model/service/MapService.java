package com.ssafy.map.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoDto;

public interface MapService {
	List<SidoDto> sidoList() throws Exception;
	List<Map<String, Object>> categoryList() throws Exception;
	List<AttractionDto> attractionList(Map<String, String> map) throws Exception;
	AttractionDto attractionDetail(int content_id) throws Exception;
}
