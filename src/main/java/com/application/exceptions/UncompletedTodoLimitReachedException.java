package com.application.exceptions;

public class UncompletedTodoLimitReachedException extends RuntimeException {
    public UncompletedTodoLimitReachedException(String message) {
        super(message);
    }
}
