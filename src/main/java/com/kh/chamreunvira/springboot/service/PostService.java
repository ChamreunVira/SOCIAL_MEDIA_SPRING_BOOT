package com.kh.chamreunvira.springboot.service;

import com.kh.chamreunvira.springboot.dto.PostRequest;
import com.kh.chamreunvira.springboot.dto.PostResponse;
import com.kh.chamreunvira.springboot.model.Post;

import java.util.List;

public interface PostService {
    Post create(PostRequest req) throws Exception;
    List<PostResponse> getAll();
    PostResponse getById(Long id);
    Post fetchPostById(Long id);
    Post update(Long id , PostRequest req);
    void delete(Long id);
}
