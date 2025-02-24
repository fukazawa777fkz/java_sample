package com.example.taskmate.api.controller;

import ch.qos.logback.core.util.StringUtil;
import com.example.taskmate.api.exception.BadRequestException;
import com.example.taskmate.api.request.TaskSearchRequest;
import com.example.taskmate.api.response.TaskListResponse;
import com.example.taskmate.entity.Task;
import com.example.taskmate.form.TaskSearchListForm;
import com.example.taskmate.service.StatusService;
import com.example.taskmate.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks") // RESTfulな設計
@RequiredArgsConstructor
public class GetTaskListController {

	private final TaskService taskService;
	private final StatusService statusService;
	
	/*--- 一覧検索リクエスト -------------------------------*/
	@PostMapping("/tasks-search")
	public TaskListResponse searchTaskList(
			@Validated @RequestBody TaskSearchRequest request,
			BindingResult result) {

		// 入力チェックでエラーがあれば例外をスロー
		if (result.hasErrors()) {
			throw new BadRequestException("Invalid input parameters.", result);
		}

		//-- form -> entity へ (検索条件は Task) --
		Task task = new Task();
		// taskName設定
		if (!StringUtil.isNullOrEmpty(request.getTaskName()) ) {
			task.setTaskName("%" + request.getTaskName() + "%");
		}

		// 一覧の条件検索
		TaskListResponse list = new TaskListResponse();
		list.setTasks(taskService.findListByConditions(task));
		return list;
	}
}
