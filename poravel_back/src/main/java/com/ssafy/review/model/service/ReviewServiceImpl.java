package com.ssafy.review.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.review.model.ReviewDto;
import com.ssafy.review.model.mapper.ReviewMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	public void WriteReview(String user_id, ReviewDto reviewDto) throws Exception {
		log.debug("WriteReview call");
		reviewDto.setUser_id(user_id);
		reviewMapper.WriteReview(reviewDto);
	}
	
	public void DeleteReview(int review_pk, String user_id) throws Exception {
		log.debug("DeleteReview call");
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReview_pk(review_pk);
		reviewDto.setUser_id(user_id);
		reviewMapper.DeleteReview(reviewDto);
	}
}
