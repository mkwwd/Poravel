package com.ssafy.board.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.board.model.AnswerDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;

public interface BoardService {
	
	BoardListDto ListArticle(Map<String, String> map) throws Exception;	// 공지사항 리스트 불러오기
	
	void writeArticle(String user_id, String article_type, BoardDto boardDto) throws Exception;				// 게시판 쓰기
	
	BoardDto detailArticle(String article_pk) throws Exception;	// 게시판 상세조회
	
	void updateArticle(BoardDto boardDto) throws Exception;				// 게시판 수정
	
	int deleteArticle(String article_pk) throws Exception;				// 게시판 삭제
	
	void writeAnswer(AnswerDto answerDto) throws Exception;				// 댓글 쓰기
	
	void updateAnswer(AnswerDto answerDto) throws Exception;			// 댓글 수정
	
	void deleteAnswer(int reply_pk) throws Exception;				// 댓글 삭제
}
