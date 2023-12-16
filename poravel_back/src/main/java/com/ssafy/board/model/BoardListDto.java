package com.ssafy.board.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardListDto {
	private List<BoardDto> articles;
	private int currentPage;
	private int totalPageCount;
	private int totalArticleCount;
}
