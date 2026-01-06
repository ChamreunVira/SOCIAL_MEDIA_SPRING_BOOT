package com.kh.chamreunvira.springboot.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Date timestamp;

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false , message , null , new Date());
    }

    public static <T> ApiResponse<T> error(String message , T data) {
        return new ApiResponse<>(false , message , data , new Date());
    }

    public static <T> ApiResponse<T> success(String message , T data) {
        return new ApiResponse<>(true , message , data , new Date());
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true , message , null, new Date());
    }


}
