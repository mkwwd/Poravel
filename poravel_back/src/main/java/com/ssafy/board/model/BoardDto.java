package com.ssafy.board.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {
	private int article_pk;
	private int article_no;
	private String subject;
	private String content;
	private String user_id;
	private int hit;
	private String register_time;
	private String article_type;
	private List<AnswerDto> answerList;
}
