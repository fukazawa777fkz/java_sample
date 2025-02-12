package com.example.taskmate.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.taskmate.entity.Status;
import com.example.taskmate.form.TaskRegistForm;
import com.example.taskmate.service.StatusService;
import com.example.taskmate.service.TaskService;

@WebMvcTest(TaskRegistController.class)
class TaskRegistControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StatusService statusService;

	@MockBean
	private TaskService taskService;

	@Nested
	class testShowRegist {

		@Test
		void test001() throws Exception {
			// ================== モック ==================
			// ステータスリストを作成
			List<Status> statusList = Arrays.asList(new Status("01", "進行中"), new Status("02", "完了"));
			when(statusService.findAll()).thenReturn(statusList);

			// ================== テスト実行 ==================
			mockMvc.perform(post("/task-show-regist"))
					.andExpect(status().isOk())
					.andExpect(model().attributeExists("statusList"))
					.andExpect(model().attribute("statusList", statusList))
					.andExpect(view().name("task-regist"));

			// ================== service呼び出し確認 ==================
			verify(statusService, times(1)).findAll();
		}
	}

	@Nested
	class confirmRegist {

		@Test
		void OK_001() throws Exception {

			// ================== モック ==================
			// フォーム
			TaskRegistForm form = new TaskRegistForm();
			form.setTaskName("タスク1");
			form.setLimitDate(java.sql.Date.valueOf("2025-12-31"));
			form.setStatusCode("01");
			form.setStatusName("進行中");
			form.setRemarks("タスクの詳細");

			// 全検索
			List<Status> statusList = Arrays.asList(new Status("01", "進行中"), new Status("02", "完了"));
			when(statusService.findAll()).thenReturn(statusList);

			// 指定検索
			when(statusService.findByCode(form.getStatusCode())).thenReturn(statusList.get(0));

			// ================== テスト実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", form)) // フォームオブジェクトを送信
					.andExpect(status().isOk())
					.andExpect(view().name("task-confirm-regist")) // 正常時の遷移先
					.andExpect(model().attributeExists("taskRegistForm"));

			// ================== service呼び出し確認 ==================
			verify(statusService, times(1)).findByCode("01");
		}
		
		@Test
		void NG_001() throws Exception {

			// ================== モック ==================
			// フォーム
			TaskRegistForm form = new TaskRegistForm();
			form.setTaskName("タスク1");
			form.setLimitDate(java.sql.Date.valueOf("2025-12-31"));
			form.setStatusCode("010");
			form.setStatusName("進行中");
			form.setRemarks("タスクの詳細");

			// ================== テスト実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", form)) // フォームオブジェクトを送信
					.andExpect(status().isOk())
					.andExpect(view().name("task-regist")) // 異常時の遷移先
					.andExpect(model().attributeExists("taskRegistForm"));

			// ================== service呼び出し確認 ==================
			verify(statusService, never()).findByCode(any());
		}
		
	}

	@Nested
	class WhenInvalidForm {

	}
}
