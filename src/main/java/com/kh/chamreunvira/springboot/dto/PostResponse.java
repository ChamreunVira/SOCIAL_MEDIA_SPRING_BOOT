package com.kh.chamreunvira.springboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String image;
    @JsonProperty("author")
    private AuthorResponse authorResponse;
    @JsonProperty("comments")
    private List<CommentResponse> comments;
    @JsonProperty("like")
    private int likeCount;
    @JsonProperty("likeByMe")
    private boolean likeByMe;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
