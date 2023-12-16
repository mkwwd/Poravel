package com.ssafy.memory.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.memory.model.MemoryDto;

@Mapper
public interface MemoryMapper {
	String NextPk() throws SQLException;					// 다음 PK번호
	List<MemoryDto> memoryList(Map<String, String> map) throws SQLException;		// 메모리 리스트
	List<MemoryDto> BestMemory() throws SQLException;		// 베스트 메모리
	void writeMemory(MemoryDto memoryDto) throws SQLException;		// 메모리 작성
	int UpdateMemory(MemoryDto memoryDto) throws SQLException;		// 메모리 수정
	int DeleteMemory(MemoryDto memoryDto) throws SQLException;		// 메모리 삭제
	List<MemoryDto> usersMemoryList(String user_id) throws SQLException;	// 한 유저가 작성한 메모리 리스트
	MemoryDto detailMemory(int memory_pk) throws SQLException;		// 메모리 상세 조회
	void increaseHit(int memory_pk) throws SQLException;			// 메모리 조회 수 증가
}
