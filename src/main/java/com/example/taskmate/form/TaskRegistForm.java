package com.example.taskmate.form;

import java.sql.Date;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRegistForm {

	@Size(min=1, max=32, message="1文字から32文字で指定してください。")
	private String taskName;

	private Date limitDate;
	
	@Size(min=2, max=2, message="指定に誤りがあります。")
	private String statusCode;
	
	private String statusName;

	@Size(max=64, message="64文字以内で指定してください。")
	private String remarks;

}
