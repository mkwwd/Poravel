package com.ssafy.memory.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.memory.model.MemoryDto;
import com.ssafy.memory.model.mapper.MemoryMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemoryServiceImpl implements MemoryService {
	
	@Autowired
	private MemoryMapper memoryMapper;

	public List<MemoryDto> memoryList(Map<String, String> map) throws Exception {						// 메모리 리스트
		log.debug("MemoryList Service");
		return memoryMapper.memoryList(map);
	}
	
	public List<MemoryDto> BestMemory() throws Exception {												// 베스트 메모리
		log.debug("BestMemory Service");
		return memoryMapper.BestMemory();
	}
	
	public void writeMemory(MemoryDto memoryDto, String user_id) throws Exception {						// 메모리 작성
		log.debug("writeMemory Service");
		memoryDto.setUser_id(user_id);
		memoryMapper.writeMemory(memoryDto);
	}
	
	public int UpdateMemory(MemoryDto memoryDto, String user_id, int memory_pk) throws Exception {		// 메모리 수정
		log.debug("UpdateMemory Service");
		memoryDto.setUser_id(user_id);
		memoryDto.setMemory_pk(memory_pk);
		return memoryMapper.UpdateMemory(memoryDto);
	}

	public int DeleteMemory(String user_id, int memory_pk) throws Exception {							// 메모리 삭제
		log.debug("DeleteMemory Service");
		MemoryDto memoryDto = new MemoryDto();
		memoryDto.setUser_id(user_id);
		memoryDto.setMemory_pk(memory_pk);
		return memoryMapper.DeleteMemory(memoryDto);
	}
	
	public MemoryDto detailMemory(int memory_pk) throws Exception {										// 메모리 상세 조회
		log.debug("detailMemory Service");
		return memoryMapper.detailMemory(memory_pk);
	}
	
	public void increaseHit(int memory_pk) throws Exception {											// 메모리 조회 수 증가
		log.debug("increaseHit Service");
		memoryMapper.increaseHit(memory_pk);
	}

}
