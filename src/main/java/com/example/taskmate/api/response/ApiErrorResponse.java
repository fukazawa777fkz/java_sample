package com.example.taskmate.api.response;

import lombok.Data;

@Data
public class ApiErrorResponse<T> {
    private int status;      // HTTPステータスコード
    private String message;  // メッセージ（成功・失敗情報など）
    private T details;       // 実際のレスポンスデータ（汎用型）

    public ApiErrorResponse(int status, String message, T details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
