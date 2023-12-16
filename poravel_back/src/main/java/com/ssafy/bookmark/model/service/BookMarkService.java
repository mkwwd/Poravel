package com.ssafy.bookmark.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.bookmark.model.BookMarkDto;

public interface BookMarkService {
	
	void AddBookMark(String user_id, BookMarkDto bookmarkDto) throws Exception;		// 북마크 추가
	
	void DeleteBookMark(int bookmark_pk) throws Exception;							// 북마크 삭제
}
