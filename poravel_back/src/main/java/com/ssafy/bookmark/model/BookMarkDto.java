package com.ssafy.bookmark.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "BookMarkDto : 북마크 정보", description = "북마크 관련 CRD API에 관한 설명")
public class BookMarkDto {
	@ApiModelProperty(value = "북마크 PK")
	private int bookmark_pk;
	@ApiModelProperty(value = "유저 ID")
	private String user_id;
	@ApiModelProperty(value = "콘텐츠 ID")
	private int content_id;
	@ApiModelProperty(value = "콘텐츠 이름")
	private String title;
	@ApiModelProperty(value = "사진")
	private String first_image;
}
