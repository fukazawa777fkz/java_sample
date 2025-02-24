package com.example.taskmate.api.response;

import lombok.Data;

import java.util.List;

public class ApiResponse<T> {
    private int statusCode;      // HTTPステータスコード
    private String message;      // メッセージ（成功・失敗情報など）
    private T data;              // 実際のレスポンスデータ（汎用型）

    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    // ゲッター、セッター (Lombokを使う場合は @Data や @Getter/@Setter でもOK)
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
