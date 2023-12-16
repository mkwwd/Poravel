package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.AnswerDto;
import com.ssafy.board.model.BoardDto;

@Mapper
public interface BoardMapper {
	
	List<BoardDto> ListArticle(Map<String, Object> map) throws SQLException;// 공지사항 리스트 불러오기
	
	int totalArticleCount(Map<String, Object> map) throws SQLException;		// 게시글 총 개수
	
	void writeArticle(BoardDto boardDto) throws SQLException;				// 게시판 쓰기
	
	BoardDto detailArticle(String article_pk) throws SQLException;	// 게시판 상세조회
	
	void updateArticle(BoardDto boardDto) throws SQLException;				// 게시판 수정
	
	int deleteArticle(String article_pk) throws SQLException;				// 게시판 삭제
	
	List<AnswerDto> detailAnswer(String article_pk) throws SQLException;	// 댓글 보기
	
	void increaseHit(String article_pk) throws SQLException;				// 조회수 1 증가
	
	void writeAnswer(AnswerDto answerDto) throws SQLException;				// 댓글 쓰기

	void updateAnswer(AnswerDto answerDto) throws SQLException;				// 댓글 수정
	
	void deleteAnswer(int reply_pk) throws SQLException;				// 댓글 삭제
}

