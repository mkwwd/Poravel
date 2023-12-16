package com.ssafy.memory.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.memory.model.MemoryDto;

public interface MemoryService {
	List<MemoryDto> memoryList(Map<String, String> map) throws Exception;						// 메모리 리스트
	List<MemoryDto> BestMemory() throws Exception;												// 베스트 메모리
	void writeMemory(MemoryDto memoryDto, String user_id) throws Exception;						// 메모리 작성
	int UpdateMemory(MemoryDto memoryDto, String user_id, int memory_pk) throws Exception;		// 메모리 수정
	int DeleteMemory(String user_id, int memory_pk) throws Exception;							// 메모리 삭제
	MemoryDto detailMemory(int memory_pk) throws Exception;										// 메모리 상세 조회
	void increaseHit(int memory_pk) throws Exception;											// 메모리 조회 수 증가
}
