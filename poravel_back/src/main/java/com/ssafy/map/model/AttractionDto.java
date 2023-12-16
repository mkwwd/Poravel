package com.ssafy.map.model;

import java.util.List;

import com.ssafy.memory.model.MemoryDto;
import com.ssafy.review.model.ReviewDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionDto {
	int content_id;
	String title;
	String addr1;
	String zipcode;
	String first_image;
	int sido_code;
	double latitude;
	double longitude;
	int category_pk;
	String overview;
	List<ReviewDto> reviewDtoList;
	List<MemoryDto> memoryDtoList;
}
