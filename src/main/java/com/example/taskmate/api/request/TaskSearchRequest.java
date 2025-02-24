package com.example.taskmate.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskSearchRequest {
    @NotBlank
    private String taskName;

    private String statusCode;
}
