package com.example.taskmate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.taskmate.entity.Task;
import com.example.taskmate.entity.TaskDetail;
import com.example.taskmate.entity.TaskSummary;

@Mapper
public interface TaskMapper extends TTaskMapper{

	// 一覧全件検索
	List<TaskSummary> selectListAll();

	// 一覧条件検索
	List<TaskSummary> selectListByConditions(@Param("task") Task task);

	// 詳細検索
	TaskDetail selectDetailByTaskId(@Param("taskId") Integer taskId);

	// 更新
	void update(@Param("task") Task task);

}
