package com.example.taskmate.form;

import lombok.Data;

@Data
public class MemoRemoveForm {

	private Integer memoId;
	private Integer taskId;
	private String memo;

}
