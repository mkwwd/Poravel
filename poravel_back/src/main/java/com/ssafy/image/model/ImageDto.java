package com.ssafy.image.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImageDto {
	private int img_pk;
	private String type;
	private String type_pk;
	private String save_folder;
	private String org_name;
	private String saved_name;
}
