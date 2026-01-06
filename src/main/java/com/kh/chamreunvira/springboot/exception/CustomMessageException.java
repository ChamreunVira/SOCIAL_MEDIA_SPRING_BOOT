package com.kh.chamreunvira.springboot.exception;

public class CustomMessageException extends RuntimeException{
    private String message;
    private boolean status;

    public CustomMessageException() {

    }

    public CustomMessageException(String message, boolean status) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
