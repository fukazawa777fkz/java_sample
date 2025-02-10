package com.example.taskmate.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Task {

	private Integer taskId;
	private String taskName;
	private Date limitDate;
	private String statusCode;
	private String remarks;

}
