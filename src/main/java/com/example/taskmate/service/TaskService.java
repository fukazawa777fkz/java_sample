package com.example.taskmate.service;

import java.util.List;

import com.example.taskmate.entity.TTask;
import com.example.taskmate.entity.Task;
import com.example.taskmate.entity.TaskDetail;
import com.example.taskmate.entity.TaskSummary;

public interface TaskService {

	// 一覧全件検索
	List<TaskSummary> findListAll();
	
	// 登録
	void regist(TTask task);

	// 一覧条件検索
	List<TaskSummary> findListByConditions(Task task);

	// 詳細検索
	TaskDetail findDetailByTaskId(Integer taskId);

	// 更新
	void edit(Task task);

}
