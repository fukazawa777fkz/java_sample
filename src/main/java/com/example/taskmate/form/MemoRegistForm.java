package com.example.taskmate.form;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemoRegistForm {

	private Integer taskId;
	
	@Size(min=1, max=64, message="1文字から64文字で指定してください。")
	private String memo;

}
