package com.ssafy.bookmark.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bookmark.model.BookMarkDto;

@Mapper
public interface BookMarkMapper {
	
	
	
	void AddBookMark(BookMarkDto bookmarkDto) throws SQLException;		// 북마크 추가
	
	void DeleteBookMark(int bookmark_pk) throws SQLException;			// 북마크 삭제
}
