package com.ssafy.image.model.service;

import java.sql.SQLException;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.image.model.ImageDto;

public interface ImageService {
	ImageDto ViewImage(String type, String type_pk) throws Exception;				// 이미지 보여주기
	boolean uploadImage(String type, String user_id, MultipartFile uploadImage) throws Exception;	// 이미지 업로드
	boolean updateImage(String memory_pk, MultipartFile uploadImage) throws Exception;
}
