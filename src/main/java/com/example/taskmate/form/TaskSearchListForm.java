package com.example.taskmate.form;

import java.sql.Date;

import lombok.Data;

@Data
public class TaskSearchListForm {

	private String taskName;
	private Date limitDate;
	private String statusCode;

}
