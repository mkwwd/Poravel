package com.ssafy.image.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.image.model.ImageDto;
import com.ssafy.image.model.mapper.ImageMapper;
import com.ssafy.memory.model.mapper.MemoryMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
	
	private ImageMapper imageMapper;
	private MemoryMapper memoryMapper;
	
	public ImageServiceImpl(ImageMapper imageMapper, MemoryMapper memoryMapper) {
		super();
		this.imageMapper = imageMapper;	
		this.memoryMapper = memoryMapper;
	}
	
	private String rootPath = "\\src\\main\\resources\\static\\img\\";
	
	
	@Override
	public ImageDto ViewImage(String type, String type_pk) throws Exception {				// 이미지 보여주기
		log.debug("ViewImage call");
		System.out.println(rootPath);
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("type_pk", type_pk);
		return imageMapper.ViewImage(map);
	}
	
	@Override
	public boolean uploadImage(			// 이미 저장된 이미지가 있다면 DB수정으로 바꿔야함
			String type,
			String user_id,
			MultipartFile uploadImage) throws Exception {							// 이미지 업로드
		if (!uploadImage.isEmpty()) {	// 파일이 입력되었을 때
			ImageDto imageDto = new ImageDto();
			String subPath = type;
			String realPath = System.getProperty("user.dir")+rootPath + subPath;
			System.out.println(realPath);
			log.debug("저장 폴더 : {}", realPath);
			File folder = new File(realPath);
			if (!folder.exists()) folder.mkdirs();
			String originalFileName = uploadImage.getOriginalFilename();
			if (!originalFileName.isEmpty()) {		//파일이름이 비어있지 않을 때
				String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
				imageDto.setSave_folder(rootPath + subPath);
				imageDto.setOrg_name(originalFileName);
				imageDto.setSaved_name(saveFileName);
				imageDto.setType(type);
				if(type.equals("memory")) {
					String memory_pk = memoryMapper.NextPk();
					if(memory_pk==null) memory_pk="1";
					imageDto.setType_pk(memory_pk);
				} else if(type.equals("user")){
					imageDto.setType_pk(user_id);
				}
				log.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", uploadImage.getOriginalFilename(), saveFileName);
				uploadImage.transferTo(new File(folder, saveFileName));
			}
			int count = imageMapper.searchAmountImage(imageDto);
			if(count==0) {								// 이미지가 DB에 없을시
				imageMapper.insertImage(imageDto);		// 이미지 DB에 추가
			}
			else if(count > 0) {						// 이미지가 DB에 있을시
				imageMapper.updateImage(imageDto);		// 이미지 DB에서 수정
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateImage(String memory_pk, MultipartFile uploadImage) throws Exception {							// 이미지 업로드
		if (!uploadImage.isEmpty()) {	// 파일이 입력되었을 때
			ImageDto imageDto = new ImageDto();
			String subPath = "memory";
			String realPath = System.getProperty("user.dir")+rootPath + subPath;
			log.debug("저장 폴더 : {}", realPath);
			File folder = new File(realPath);
			if (!folder.exists()) folder.mkdirs();
			String originalFileName = uploadImage.getOriginalFilename();
			if (!originalFileName.isEmpty()) {		//파일이름이 비어있지 않을 때
				String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
				imageDto.setSave_folder(rootPath + subPath);
				imageDto.setOrg_name(originalFileName);
				imageDto.setSaved_name(saveFileName);
				imageDto.setType("memory");
				imageDto.setType_pk(memory_pk);
				log.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", uploadImage.getOriginalFilename(), saveFileName);
				uploadImage.transferTo(new File(folder, saveFileName));
			}
			int count = imageMapper.searchAmountImage(imageDto);
			if(count==0) {								// 이미지가 DB에 없을시
				imageMapper.insertImage(imageDto);		// 이미지 DB에 추가
			}
			else if(count > 0) {						// 이미지가 DB에 있을시
				imageMapper.updateImage(imageDto);		// 이미지 DB에서 수정
			}
			return true;
		}
		return false;
	}
}
