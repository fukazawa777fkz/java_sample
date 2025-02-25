package com.example.taskmate.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private final BindingResult result;

    public BadRequestException(String message, BindingResult result) {
        super(message);
        this.result = result;
    }

}