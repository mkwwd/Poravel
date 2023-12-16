package com.ssafy.map.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.map.model.AttractionDto;
import com.ssafy.map.model.SidoDto;

@Mapper
public interface MapMapper {
	List<SidoDto> sidoList() throws SQLException;
	List<Map<String, Object>> categoryList() throws SQLException;
	List<AttractionDto> attractionCategoryList(AttractionDto attractionDto) throws SQLException;
	List<AttractionDto> attractionSearchList(String title) throws SQLException;
	AttractionDto attractionDetail(int content_id) throws SQLException;
}
