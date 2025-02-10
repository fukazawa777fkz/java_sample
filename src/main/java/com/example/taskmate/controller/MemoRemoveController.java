package com.example.taskmate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.taskmate.form.MemoRemoveForm;
import com.example.taskmate.service.MemoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemoRemoveController {

	private final MemoService memoService;
	
	/*--- メモ削除リクエスト（メモ一覧画面より） ---*/
	@PostMapping("/memo-remove")
	public String remove(
			@ModelAttribute MemoRemoveForm form) {

		// メモ削除確認画面に遷移する
		return "memo-confirm-remove";
	}

	/*--- メモ削除リクエスト（削除確認画面より） ---*/
	@PostMapping("/memo-confirm-remove")
	public String confirmRemove(
			@ModelAttribute MemoRemoveForm form,
			RedirectAttributes redirectAttributes) {

		// 削除処理
		memoService.remove(form.getMemoId());
		
		// フラッシュスコープに完了メッセージを表示して リダイレクト
		redirectAttributes.addFlashAttribute("msg", "(メモ削除)");
		redirectAttributes.addFlashAttribute("taskId", form.getTaskId());
		
		return "redirect:/memo-complete";
	}

}
