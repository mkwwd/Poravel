package com.ssafy.memory.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemoryDto {
	private int memory_pk;
	private String user_id;
	private String user_name;
	private String location;
	private String comment;
	private int memory_hit;
	private String memory_date;
	private String latitude;
	private String longitude;
}
