package com.example.taskmate.api.advice;
import com.example.taskmate.api.exception.BadRequestException;
import com.example.taskmate.api.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("errors", formatFieldErrors(ex.getResult().getFieldErrors()));
        // ApiResponseを構築
        ApiResponse<Object> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), errorDetails);
        // ResponseEntityを返却
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class) // 汎用例外ハンドラ
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        // 汎用エラーのレスポンス
        ApiResponse<String> response = new ApiResponse<>(500, "Internal Server Error", ex.getMessage());

        // ResponseEntityを返却
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private List<Map<String, Object>> formatFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream().map(fieldError -> {
            Map<String, Object> error = new HashMap<>();
            error.put("field", fieldError.getField());                         // エラー対象のフィールド名
            error.put("defaultMessage", fieldError.getDefaultMessage());       // デフォルトメッセージ
            error.put("code", fieldError.getCode());                           // エラーメッセージコード
            return error;
        }).collect(Collectors.toList());
    }

}