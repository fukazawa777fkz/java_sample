package com.example.taskmate.api.controller;

import com.example.taskmate.api.request.TaskSearchRequest;
import com.example.taskmate.api.response.ApiErrorResponse;
import com.example.taskmate.api.response.TaskListResponse;
import com.example.taskmate.entity.Task;
import com.example.taskmate.entity.TaskSummary;
import com.example.taskmate.service.StatusService;
import com.example.taskmate.service.TaskService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.bytebuddy.build.Plugin.Engine.Summary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetTaskListController.class)
public class GetTaskListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private StatusService statusService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchTaskList() throws Exception {

        List<TaskSummary> list = new ArrayList<>();
        {
            TaskSummary summary = new TaskSummary();
            summary.setTaskId(1);
            summary.setTaskName("Test Task1");
            list.add(summary);
        }
        {
            TaskSummary summary = new TaskSummary();
            summary.setTaskId(2);
            summary.setTaskName("Test Task2");
            list.add(summary);
        }

        when(taskService.findListByConditions(any(Task.class)))
                .thenReturn(list);

        // リクエストボディの作成
        TaskSearchRequest request = new TaskSearchRequest();
        request.setTaskName("Test Task");

        // リクエストの実行と検証
        MvcResult result = mockMvc.perform(post("/api/tasks/tasks-search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // レスポンスの内容を取得して検証
        String jsonResponse = result.getResponse().getContentAsString();
        ApiErrorResponse<TaskListResponse> apiResponse = objectMapper.readValue(jsonResponse, new TypeReference<ApiErrorResponse<TaskListResponse>>() {});

        // 期待値の確認
        assert apiResponse != null;
        assert apiResponse.getStatus() == 200;
        assert "Success".equals(apiResponse.getMessage());

        TaskListResponse taskListResponse = apiResponse.getDetails();
        assert taskListResponse != null;
        assert taskListResponse.getTasks().size() == 2;
        assert taskListResponse.getTasks().get(0).getTaskId() == 1;
        assert taskListResponse.getTasks().get(1).getTaskId() == 2;
        assert taskListResponse.getTasks().get(0).getTaskName().equals("Test Task1");
        assert taskListResponse.getTasks().get(1).getTaskName().equals("Test Task2");
    }
}