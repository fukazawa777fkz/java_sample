package com.example.taskmate.web.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.taskmate.web.controller.TaskRegistController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.taskmate.entity.Status;
import com.example.taskmate.form.TaskRegistForm;
import com.example.taskmate.service.StatusService;
import com.example.taskmate.service.TaskService;

@SpringBootTest
class TaskRegistControllerTest {

	@Nested
	class testShowRegist {
		private MockMvc mockMvc;

		@InjectMocks // モックを TaskRegistController に注入する
		private TaskRegistController taskRegistController;

		@Mock
		private StatusService statusService;

		@Mock
		private TaskService taskService;

		@BeforeEach
		void setup() {
			// 手動で MockMvc をセットアップ
			mockMvc = MockMvcBuilders.standaloneSetup(taskRegistController).build();
		}

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
					.andExpect(view().name("task-regist-view"));

			// ================== service呼び出し確認 ==================
			verify(statusService, times(1)).findAll();
		}
	}

	@Nested
	@SpringBootTest
	class taskRegist {

		private MockMvc mockMvc;

		@InjectMocks // モックを TaskRegistController に注入する
		private TaskRegistController taskRegistController;

		@Mock
		private StatusService statusService;

		@Mock
		private TaskService taskService;

		@BeforeEach
		void setup() {
			// 手動で MockMvc をセットアップ
			mockMvc = MockMvcBuilders.standaloneSetup(taskRegistController).build();
		}

		private void defaultMock() {
			List<Status> statusList = Arrays.asList(new Status("01", "進行中"), new Status("02", "完了"));
			when(statusService.findAll()).thenReturn(statusList);
			when(statusService.findByCode(any())).thenReturn(statusList.get(0));
		}

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
			// ステータスリストを作成
			List<Status> statusList = Arrays.asList(new Status("01", "進行中"), new Status("02", "完了"));
			when(statusService.findAll()).thenReturn(statusList);

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
					.andExpect(view().name("task-regist-view")) // 異常時の遷移先
					.andExpect(model().attributeExists("taskRegistForm"));

			// ================== service呼び出し確認 ==================
			verify(statusService, never()).findByCode(any());
		}

		@Test
		public void 正常系_001_最小() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			taskRegistForm.setTaskName("a");
			taskRegistForm.setLimitDate(java.sql.Date.valueOf("2025-02-13"));
			taskRegistForm.setStatusCode("aa");
			taskRegistForm.setStatusName("zz");
			taskRegistForm.setRemarks("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-confirm-regist"))
					.andExpect(model().hasNoErrors());
		}

		@Test
		public void 正常系_002_最大() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			taskRegistForm.setTaskName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			taskRegistForm.setLimitDate(java.sql.Date.valueOf(LocalDate.now().toString()));
			taskRegistForm.setStatusCode("aa");
			taskRegistForm.setStatusName("zz");
			taskRegistForm.setRemarks("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-confirm-regist"))
					.andExpect(model().hasNoErrors());
		}

		@Test
		public void 正常系_003_必須のみ() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-confirm-regist"))
					.andExpect(model().hasNoErrors());
		}

		@Test
		public void 正常系_004_空文字() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			taskRegistForm.setTaskName("a");
			taskRegistForm.setLimitDate(java.sql.Date.valueOf("2025-02-13"));
			taskRegistForm.setStatusCode("11");
			taskRegistForm.setStatusName("");
			taskRegistForm.setRemarks("");
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-confirm-regist"))
					.andExpect(model().hasNoErrors());
		}

		@Test
		public void 正常系_005_null値() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			taskRegistForm.setTaskName("z");
			taskRegistForm.setLimitDate(java.sql.Date.valueOf("2025-02-13"));
			taskRegistForm.setStatusCode("zz");
			taskRegistForm.setStatusName("zz");
			taskRegistForm.setRemarks("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-confirm-regist"))
					.andExpect(model().hasNoErrors());
		}

		@Test
		public void 正常系_006_半角スペース() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			taskRegistForm.setTaskName(" ");
			taskRegistForm.setLimitDate(java.sql.Date.valueOf("2025-02-13"));
			taskRegistForm.setStatusCode("  ");
			taskRegistForm.setStatusName(" ");
			taskRegistForm.setRemarks("                                                                ");
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-confirm-regist"))
					.andExpect(model().hasNoErrors());
		}

		@Test
		public void 正常系_007_全角スペース() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			taskRegistForm.setTaskName("　");
			taskRegistForm.setLimitDate(java.sql.Date.valueOf("2025-02-13"));
			taskRegistForm.setStatusCode("　　");
			taskRegistForm.setStatusName("　");
			taskRegistForm.setRemarks("　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　");
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-confirm-regist"))
					.andExpect(model().hasNoErrors());
		}

		@Test
		public void 異常系_008_最小() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			taskRegistForm.setTaskName("");
			taskRegistForm.setLimitDate(java.sql.Date.valueOf("2025-02-13"));
			taskRegistForm.setStatusCode("a");
			taskRegistForm.setStatusName("zz");
			taskRegistForm.setRemarks("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-regist-view"))
					.andExpect(model().attributeHasFieldErrors("taskRegistForm", "taskName", "statusCode"))
					.andExpect(model().attributeHasFieldErrorCode("taskRegistForm", "taskName", "Size"))
					.andExpect(model().attributeHasFieldErrorCode("taskRegistForm", "statusCode", "Size"));
		}

		@Test
		public void 異常系_009_最大() throws Exception {

			// ================== リクエスト ==================
			TaskRegistForm taskRegistForm = new TaskRegistForm();
			taskRegistForm.setTaskName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			taskRegistForm.setLimitDate(java.sql.Date.valueOf("2025-02-13"));
			taskRegistForm.setStatusCode("aaa");
			taskRegistForm.setStatusName("zz");
			taskRegistForm.setRemarks("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
			// ================== モック ==================
			defaultMock();

			// ================== 実行 ==================
			mockMvc.perform(post("/task-regist")
					.flashAttr("taskRegistForm", taskRegistForm)) // フォームオブジェクト
					.andExpect(status().isOk())
					.andExpect(view().name("task-regist-view"))
					.andExpect(model().attributeHasFieldErrors("taskRegistForm", "taskName", "statusCode"))
					.andExpect(model().attributeHasFieldErrorCode("taskRegistForm", "taskName", "Size"))
					.andExpect(model().attributeHasFieldErrorCode("taskRegistForm", "statusCode", "Size"));
		}

	}

	@Nested
	class WhenInvalidForm {

	}
}
