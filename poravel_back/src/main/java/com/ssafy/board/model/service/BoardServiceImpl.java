package com.ssafy.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.AnswerDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class BoardServiceImpl implements BoardService{

	@Value("${BoardSizePerPage}")
	private int BoardSizePerPage;
	
	private BoardMapper boardMapper;
	
	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	
	@Override
	public BoardListDto ListArticle(Map<String, String> map) throws Exception {	// 리스트 불러오기
		log.debug("ListnoticeArticle call");
		
		int pgno = Integer.parseInt(map.get("pgno"));
		int sttBoardNum = (pgno -1) * BoardSizePerPage;
		
		Map<String, Object> ListArticleParam = new HashMap<String, Object>();		
		ListArticleParam.put("article_type", map.get("article_type"));
		ListArticleParam.put("key", map.get("key"));
		ListArticleParam.put("word", map.get("word"));
		ListArticleParam.put("stt_board_num", sttBoardNum);		// 시작 board 번호
		ListArticleParam.put("list_size", BoardSizePerPage);	// 리스트 개수 
		
		List<BoardDto> boardDtoList = boardMapper.ListArticle(ListArticleParam);
		
		Map<String, Object> totalArticleCountParam = new HashMap<String, Object>();
		totalArticleCountParam.put("article_type", map.get("article_type"));
		totalArticleCountParam.put("key", map.get("key"));
		totalArticleCountParam.put("word", map.get("word"));
		
		int totalArticleCount = boardMapper.totalArticleCount(totalArticleCountParam);
				
		int totalPageCount = (totalArticleCount - 1) / BoardSizePerPage + 1;
		
		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(boardDtoList);
		boardListDto.setCurrentPage(pgno);
		boardListDto.setTotalPageCount(totalPageCount);
		boardListDto.setTotalArticleCount(totalArticleCount);
		
		return boardListDto;
	}
	
	@Override
	public void writeArticle(String user_id, String article_type, BoardDto boardDto) throws Exception {				// 게시판 쓰기
		log.debug("writeArticle call");
		boardDto.setArticle_type(article_type);
		boardDto.setUser_id(user_id);
		boardMapper.writeArticle(boardDto);
	}
	
	@Override
	public BoardDto detailArticle(String article_pk) throws Exception {	// 게시판 상세조회
		log.debug("detailArticle call");
		
		boardMapper.increaseHit(article_pk);				// 조회수 1 증가
		
		BoardDto boardDto = boardMapper.detailArticle(article_pk);
		List<AnswerDto> answerDtoList = boardMapper.detailAnswer(article_pk);
		boardDto.setAnswerList(answerDtoList);
		
		return boardDto;
	}
	
	@Override
	public void updateArticle(BoardDto boardDto) throws Exception {				// 게시판 수정
		log.debug("updateArticle call");
		boardMapper.updateArticle(boardDto);
	}
	
	@Override
	public int deleteArticle(String article_pk) throws Exception {				// 게시판 삭제
		log.debug("deleteArticle call {}", article_pk);
		return boardMapper.deleteArticle(article_pk);
	}
	
	@Override
	public void writeAnswer(AnswerDto answerDto) throws Exception {				// 댓글 쓰기
		log.debug("writeAnswer call");
		boardMapper.writeAnswer(answerDto);
	}
	
	@Override
	public void updateAnswer(AnswerDto answerDto) throws Exception {			// 댓글 수정
		log.debug("updateAnswer call");
		boardMapper.updateAnswer(answerDto);
	}
	
	@Override
	public void deleteAnswer(int reply_pk) throws Exception {				// 댓글 삭제
		log.debug("deleteAnswer call");
		boardMapper.deleteAnswer(reply_pk);
	}
	
}
