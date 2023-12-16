package com.ssafy.review.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.review.model.ReviewDto;

public interface ReviewService {
	void WriteReview(String user_id, ReviewDto reviewDto) throws Exception;
	void DeleteReview(int review_pk, String user_id) throws Exception;
}
