package com.example.taskmate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmate.entity.Task;
import com.example.taskmate.entity.TaskDetail;
import com.example.taskmate.entity.TaskSummary;
import com.example.taskmate.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<TaskSummary> findListAll() {

		List<TaskSummary> list = taskRepository.selectListAll();
		
		return list;

	}

	@Override
	@Transactional
	public void regist(Task task) {

		taskRepository.insert(task);

	}

	@Override
	@Transactional(readOnly = true)
	public List<TaskSummary> findListByConditions(Task task) {

		List<TaskSummary> list = taskRepository.selectListByConditions(task);
		
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public TaskDetail findDetailByTaskId(Integer taskId) {

		TaskDetail taskDetail = taskRepository.selectDetailByTaskId(taskId);
		
		return taskDetail;

	}

	@Override
	@Transactional
	public void edit(Task task) {

		taskRepository.update(task);
		
	}

}
