package com.kh.chamreunvira.springboot.service;

import com.kh.chamreunvira.springboot.dto.PostRequest;
import com.kh.chamreunvira.springboot.dto.PostResponse;
import com.kh.chamreunvira.springboot.model.Post;

import java.util.List;

public interface PostService {
    Post create(PostRequest req , String email);
    List<PostResponse> getAll(String email);
    PostResponse getById(Long id , String email);
    Post fetchPostById(Long id);
    Post update(Long id , PostRequest req);
    void delete(Long id);
}
