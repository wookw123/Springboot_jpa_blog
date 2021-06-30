package com.cos.blog.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplySaveDto<T> {
	private int userId;
	private int boardId;
	private String content;
}
