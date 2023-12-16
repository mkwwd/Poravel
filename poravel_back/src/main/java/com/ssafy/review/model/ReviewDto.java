package com.ssafy.review.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDto {
	private int review_pk;
	private String user_id;
	private int content_id;
	private String comment;
}
