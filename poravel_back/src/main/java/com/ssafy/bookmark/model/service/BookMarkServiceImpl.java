package com.ssafy.bookmark.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.bookmark.model.BookMarkDto;
import com.ssafy.bookmark.model.mapper.BookMarkMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookMarkServiceImpl implements BookMarkService {

	private BookMarkMapper bookmarkMapper;

	@Autowired
	public BookMarkServiceImpl(BookMarkMapper bookmarkMapper) {
		super();
		this.bookmarkMapper = bookmarkMapper;
	}

	
	@Override
	public void AddBookMark(String user_id, BookMarkDto bookmarkDto) throws Exception {		// 북마크 추가
		log.debug("insertBookMark call");
		bookmarkDto.setUser_id(user_id);
		bookmarkMapper.AddBookMark(bookmarkDto);
	}
	
	@Override
	public void DeleteBookMark(int bookmark_pk) throws Exception {							// 북마크 삭제
		log.debug("DeleteBookMark call");
		bookmarkMapper.DeleteBookMark(bookmark_pk);
	}
	
	
	
}
