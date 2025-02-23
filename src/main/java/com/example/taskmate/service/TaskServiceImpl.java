package com.example.taskmate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmate.entity.TTask;
import com.example.taskmate.entity.Task;
import com.example.taskmate.entity.TaskDetail;
import com.example.taskmate.entity.TaskSummary;
import com.example.taskmate.mapper.TTaskMapper;
import com.example.taskmate.mapper.TaskMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskMapper taskMapper;
	private final TTaskMapper ttaskMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<TaskSummary> findListAll() {

		List<TaskSummary> list = taskMapper.selectListAll();
		
		return list;

	}

	@Override
	@Transactional
	public void regist(TTask task) {

		ttaskMapper.insert(task);

	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskSummary> findListByConditions(Task task) {

		List<TaskSummary> list = taskMapper.selectListByConditions(task);
		
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public TaskDetail findDetailByTaskId(Integer taskId) {

		TaskDetail taskDetail = taskMapper.selectDetailByTaskId(taskId);
		
		return taskDetail;

	}

	@Override
	@Transactional
	public void edit(Task task) {

		taskMapper.update(task);
		
	}

}
