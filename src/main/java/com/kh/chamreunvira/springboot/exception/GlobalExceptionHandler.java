package com.kh.chamreunvira.springboot.exception;

import com.kh.chamreunvira.springboot.data.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomMessageException.class)
    public ResponseEntity<ApiResponse<CustomMessageException>> handleCustomMessageException(CustomMessageException ex) {
        CustomMessageException customMessageException = new CustomMessageException();
        customMessageException.setMessage(ex.getLocalizedMessage());
        customMessageException.setStatus(false);
        return new ResponseEntity<>(ApiResponse.error("error from exception." , customMessageException) , HttpStatus.OK);
    }
}
