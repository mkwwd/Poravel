package com.ssafy.review.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.review.model.ReviewDto;

@Mapper
public interface ReviewMapper {
	List<ReviewDto> ListReview(int content_id) throws SQLException;
	void WriteReview(ReviewDto reviewDto) throws SQLException;
	void DeleteReview(ReviewDto reviewDto) throws SQLException;
}
