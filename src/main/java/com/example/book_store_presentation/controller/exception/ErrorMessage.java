package com.example.book_store_presentation.controller.exception;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

public class ErrorMessage {

    private final String error;
    private final String message;

    public ErrorMessage(Exception exception) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}