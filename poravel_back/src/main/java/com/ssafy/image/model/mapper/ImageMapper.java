package com.ssafy.image.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.image.model.ImageDto;

@Mapper
public interface ImageMapper {
	ImageDto ViewImage(Map<String, String> map) throws SQLException;			// 이미지 보여주기
	void insertImage(ImageDto imageDto) throws SQLException;					// 이미지 추가
	void updateImage(ImageDto imageDto) throws SQLException;					// 이미지 업데이트
	int searchAmountImage(ImageDto imageDto) throws SQLException;				// 이미지 개수 출력
}
