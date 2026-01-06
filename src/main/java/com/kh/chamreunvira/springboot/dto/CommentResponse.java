package com.kh.chamreunvira.springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class    CommentResponse {

    private Long id;
    private String content;
    private LocalDate createAt;
    private Long userId;
    private String username;

}
