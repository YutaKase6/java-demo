package com.example.javademo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // validationに失敗したフィールドのリストを取得
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        // レスポンスの"Detail"に格納するために、validationに失敗したフィールドと失敗理由を連結
        StringBuilder errorDetailStr = new StringBuilder();
        fieldErrors.forEach(fieldError ->
                errorDetailStr.append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append(", ")
        );
        ResponseError body = new ResponseError("Bad Request", errorDetailStr.toString(), "");

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handle500(Exception ex, WebRequest request) {
        log.error("Internal Server Error", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ResponseError body = new ResponseError("Internal Server Error", "", "");
        return this.handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (!(body instanceof ResponseError)) {
            body = new ResponseError(status.getReasonPhrase(), "", "");
        }

        return new ResponseEntity<>(body, headers, status);
    }
}