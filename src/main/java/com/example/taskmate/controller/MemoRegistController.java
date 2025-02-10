package com.example.taskmate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.taskmate.entity.Memo;
import com.example.taskmate.form.MemoRegistForm;
import com.example.taskmate.service.MemoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemoRegistController {

	private final MemoService memoService;

	/*--- メモ登録画面表示リクエスト ---*/
	@PostMapping("/memo-show-regist")
	public String showRegist(@ModelAttribute MemoRegistForm form) {
		// 登録画面へ
		return "memo-regist";
	}

	/*--- メモ登録リクエスト（登録画面より） ---*/
	@PostMapping("/memo-regist")
	public String regist(
			@Validated @ModelAttribute MemoRegistForm form,
			BindingResult result) {

		// 入力エラーがある場合には メモ登録画面に戻す
		if (result.hasErrors()) {
			return "memo-regist";
		}
		
		// 正常な場合に メモ登録確認画面に遷移する
		return "memo-confirm-regist";
	}

	/*--- メモ登録リクエスト（登録確認画面より） ---*/
	@PostMapping("/memo-confirm-regist")
	public String confirmRegist(
			@Validated @ModelAttribute MemoRegistForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがある場合には メモ登録画面に戻す
		if (result.hasErrors()) {
			return "memo-regist";
		}

		// form -> entity へ
		Memo memo = new Memo();
		memo.setTaskId(form.getTaskId());
		memo.setMemo(form.getMemo());
		
		// 登録処理
		memoService.regist(memo);

		// フラッシュスコープに完了メッセージを表示して リダイレクト
		redirectAttributes.addFlashAttribute("msg", "(メモ登録)");
		redirectAttributes.addFlashAttribute("taskId", form.getTaskId());
		
		return "redirect:/memo-complete";
	}

}
