package com.kh.chamreunvira.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

public class CustomMessageException extends RuntimeException{
    private String message;
    private int status;
    @JsonIgnore
    private Throwable cause;
    @JsonIgnore StackTraceElement[] stackTrace;

    public CustomMessageException() {}

    public CustomMessageException(String message , int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }
}
