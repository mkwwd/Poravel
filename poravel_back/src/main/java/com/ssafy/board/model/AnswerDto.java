package com.ssafy.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerDto {
	int reply_pk;
	int article_pk;
	String user_id;
	String content;
}
