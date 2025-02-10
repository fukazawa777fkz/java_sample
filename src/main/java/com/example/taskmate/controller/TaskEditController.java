package com.example.taskmate.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.taskmate.entity.Status;
import com.example.taskmate.entity.Task;
import com.example.taskmate.form.TaskEditForm;
import com.example.taskmate.service.StatusService;
import com.example.taskmate.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskEditController {

	private final StatusService statusService;
	private final TaskService taskService;
	
	/*--- タスク編集画面表示リクエスト ---*/
	@PostMapping("/task-show-edit")
	public String showEdit(@ModelAttribute TaskEditForm form,
			Model model) {
		
		// ステータスリストを取得し Model に設定
		List<Status> list = statusService.findAll();
		model.addAttribute("statusList", list);

		// 編集画面へ
		return "task-edit";
	}

	/*--- タスク更新リクエスト（編集画面より） ---*/
	@PostMapping("/task-edit")
	public String edit(
			@Validated @ModelAttribute TaskEditForm form,
			BindingResult result,
			Model model) {

		// 入力エラーがある場合には タスク編集画面に戻す
		if (result.hasErrors()) {
			
			// ステータスリストを取得し Model に設定
			List<Status> list = statusService.findAll();
			model.addAttribute("statusList", list);

			return "task-edit";
		}
		
		// ステータ名を form に設定 (Model内)
		Status status = statusService.findByCode(form.getStatusCode());
		form.setStatusName(status.getStatusName());
		
		// 正常な場合に タスク編集確認画面に遷移する
		return "task-confirm-edit";
	}

	/*--- タスク更新実行リクエスト（編集確認画面より） ---*/
	@PostMapping("/task-confirm-edit")
	public String confirmEdit(
			@Validated @ModelAttribute TaskEditForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		// 入力エラーがある場合には タスク編集画面に戻す
		if (result.hasErrors()) {
			
			// ステータスリストを Model に設定
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);

			return "task-edit";
		}

		// form -> entity へ
		Task task = new Task();
		task.setTaskId(form.getTaskId());
		task.setTaskName(form.getTaskName());
		task.setLimitDate(form.getLimitDate());
		task.setStatusCode(form.getStatusCode());
		task.setRemarks(form.getRemarks());
		
		// 更新処理
		taskService.edit(task);
		
		// フラッシュスコープに完了メッセージを表示して リダイレクト
		redirectAttributes.addFlashAttribute("msg", "(タスク更新)");
		
		return "redirect:/task-complete";
	}

}
