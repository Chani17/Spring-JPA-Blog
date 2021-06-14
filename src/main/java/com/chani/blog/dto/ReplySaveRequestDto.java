package com.chani.blog.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplySaveRequestDto {

	private int userId;
	
	private int boardId;
	
	private String content;
}
