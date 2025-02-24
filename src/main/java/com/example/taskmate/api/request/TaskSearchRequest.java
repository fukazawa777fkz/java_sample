package com.example.taskmate.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;

@Data
public class TaskSearchRequest {
    @NotBlank
    private String taskName;

    private String statusCode;
}
