package com.example.taskmate.entity;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class TaskDetail {

	private Integer taskId;
	private String taskName;
	private Date limitDate;
	private Status status;
	private String remarks;
	private List<Memo> memoList;
	
}
