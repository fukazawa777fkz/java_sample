package com.example.taskmate.api.controller;

import ch.qos.logback.core.util.StringUtil;
import com.example.taskmate.api.exception.BadRequestException;
import com.example.taskmate.api.request.TaskSearchRequest;
import com.example.taskmate.api.response.TaskListResponse;
import com.example.taskmate.entity.Task;
import com.example.taskmate.service.TaskService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tasks") // RESTfulな設計
@RequiredArgsConstructor
public class GetTaskListController {

	private final TaskService taskService;
	
	/*--- 一覧検索リクエスト -------------------------------*/
	@PostMapping("/tasks-search")
	
	public ResponseEntity<TaskListResponse> searchTaskList(
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
		TaskListResponse taskList = new TaskListResponse();
		taskList.setTasks(taskService.findListByConditions(task));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Connection", "Keep-Alive");
		return new ResponseEntity<TaskListResponse>(taskList, headers, HttpStatus.OK);

	}
}
