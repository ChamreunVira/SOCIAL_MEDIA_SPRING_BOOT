package com.kh.chamreunvira.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomMessageException extends RuntimeException{
    private String message;
    private boolean status;
}
