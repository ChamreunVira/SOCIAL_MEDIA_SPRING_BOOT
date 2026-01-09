package com.kh.chamreunvira.springboot.controller;

import com.kh.chamreunvira.springboot.data.ApiResponse;
import com.kh.chamreunvira.springboot.dto.PostRequest;
import com.kh.chamreunvira.springboot.dto.PostResponse;
import com.kh.chamreunvira.springboot.model.Post;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.repository.UserRepository;
import com.kh.chamreunvira.springboot.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Post>> createPost(@ModelAttribute PostRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Create post successfully." , postService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getOnePost(@PathVariable Long id) {
        return ResponseEntity.ok().body(ApiResponse.success("Post is found." , postService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPost() {
        return ResponseEntity.ok().body(ApiResponse.success("All post list." , postService.getAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> updatePost(@PathVariable Long id , @RequestBody PostRequest request) {
        return ResponseEntity.ok().body(ApiResponse.success("Update post successfully." , postService.update(id , request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }

}
