package com.example.taskmate.api.response;

import com.example.taskmate.entity.TaskSummary;
import lombok.Data;

import java.util.List;

@Data
public class TaskListResponse {
    List<TaskSummary> tasks;
}
