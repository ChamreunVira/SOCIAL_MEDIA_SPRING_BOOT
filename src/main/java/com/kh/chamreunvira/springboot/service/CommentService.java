package com.kh.chamreunvira.springboot.service;

import com.kh.chamreunvira.springboot.dto.CommentRequest;
import com.kh.chamreunvira.springboot.model.Comment;

public interface CommentService {
     Comment create(Long postId , String email , CommentRequest request);
     Comment update(Long commentId , CommentRequest request);
     void delete(Long commentId);
}
