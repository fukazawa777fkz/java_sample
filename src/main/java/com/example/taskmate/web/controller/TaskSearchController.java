package com.example.taskmate.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.taskmate.entity.Status;
import com.example.taskmate.entity.Task;
import com.example.taskmate.entity.TaskDetail;
import com.example.taskmate.entity.TaskSummary;
import com.example.taskmate.form.TaskSearchDetailForm;
import com.example.taskmate.form.TaskSearchListForm;
import com.example.taskmate.service.StatusService;
import com.example.taskmate.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskSearchController {

	private final TaskService taskService;
	private final StatusService statusService;
	
	/*--- 最初のリクエスト -------------------------------*/
	@GetMapping("/top")
	private String showListSelection(
			@ModelAttribute TaskSearchListForm form,
			Model model) {
		
		// ステータスリストを Model に設定
		List<Status> list = statusService.findAll();
		model.addAttribute("statusList", list);

		// HTMLテンプレート名で return
		return "task-list";
	}
	
	/*--- 一覧検索リクエスト -------------------------------*/
	@PostMapping("/task-search-list")
	private String searchList(
			@Validated @ModelAttribute TaskSearchListForm form,
			BindingResult result,
			Model model) {
		
		//-- form -> entity へ (検索条件は Task) --
		Task task = new Task();
		// taskName設定
		if (!form.getTaskName().equals("")) {
			task.setTaskName("%" + form.getTaskName() + "%");
		}
		// limitDate設定
		task.setLimitDate(form.getLimitDate());
		// statusCode設定
		if (!form.getStatusCode().equals("")) {
			task.setStatusCode(form.getStatusCode());
		}
		
		// 一覧の条件検索
		List<TaskSummary> list
			= taskService.findListByConditions(task);

		// ステータスリストを Model に設定（次回検索用）
		List<Status> statusList = statusService.findAll();
		model.addAttribute("statusList", statusList);
		
		// 結果を格納して HTMLテンプレート名で reutrn
		model. addAttribute("taskSummaryList", list);

		return "task-list";
	}

	/*--- 詳細検索リクエスト -------------------------------*/
	@PostMapping("/task-search-detail")
	private String searchDetail(
			TaskSearchDetailForm form,
			Model model) {

		// 詳細検索
		TaskDetail taskDetail
			= taskService.findDetailByTaskId(form.getTaskId());
		
		// 結果をModelに格納して 詳細画面へ
		model.addAttribute("taskDetail", taskDetail);
		
		return "task-detail";
	}

}
