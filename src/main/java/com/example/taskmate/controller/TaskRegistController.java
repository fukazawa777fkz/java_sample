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
import com.example.taskmate.form.TaskRegistForm;
import com.example.taskmate.service.StatusService;
import com.example.taskmate.service.TaskService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TaskRegistController {

	private final StatusService statusService;
	private final TaskService taskService;
	
	/*--- タスク登録画面表示リクエスト ---*/
	@PostMapping("/task-show-regist")
	public String showRegist(@ModelAttribute TaskRegistForm form,
			Model model) {
		
		// ステータスリストを取得し Model に設定
		List<Status> list = statusService.findAll();
		model.addAttribute("statusList", list);

		// 登録画面へ
		return "task-regist";
	}

	/*--- タスク登録リクエスト（登録画面より） ---*/
	/**
	 *  タスク登録リクエスト（登録画面より）
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/task-regist")
	public String regist(
			@Validated @ModelAttribute TaskRegistForm form,
			BindingResult result,
			Model model) {

		// 入力エラーがある場合には タスク登録画面に戻す
		if (result.hasErrors()) {
			
			// ステータスリストを取得し Model に設定
			List<Status> list = statusService.findAll();
			model.addAttribute("statusList", list);

			return "task-regist";
		}
		
		// ステータ名を form に設定 (Model内)
		Status status = statusService.findByCode(form.getStatusCode());
		form.setStatusName(status.getStatusName());
		
		// 正常な場合に タスク登録確認画面に遷移する
		return "task-confirm-regist";
	}

	/*--- タスク登録リクエスト（登録確認画面より） ---*/
	@PostMapping("/task-confirm-regist")
	public String confirmRegist(
			@Validated @ModelAttribute TaskRegistForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {

		// 入力エラーがある場合には タスク登録画面に戻す
		if (result.hasErrors()) {
			
			// ステータスリストを Model に設定
			List<Status> statusList = statusService.findAll();
			model.addAttribute("statusList", statusList);

			return "task-regist";
		}

		// form -> entity へ
		Task task = new Task();
		task.setTaskName(form.getTaskName());
		task.setLimitDate(form.getLimitDate());
		task.setStatusCode(form.getStatusCode());
		task.setRemarks(form.getRemarks());
		
		// 登録処理
		taskService.regist(task);
		
		// フラッシュスコープに完了メッセージを表示して リダイレクト
		redirectAttributes.addFlashAttribute("msg", "(タスク登録)");
		
		return "redirect:/task-complete";
	}

}
