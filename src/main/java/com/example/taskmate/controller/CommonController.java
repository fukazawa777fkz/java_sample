package com.example.taskmate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

	/*--- 完了後のリダイレクト先（タスク更新系） ---*/
	@GetMapping("/task-complete")
	private String completeTask() {
		return "task-complete";
	}

	/*--- 完了後のリダイレクト先（メモ更新系） ---*/
	@GetMapping("/memo-complete")
	private String completeMemo() {
		return "memo-complete";
	}

}
