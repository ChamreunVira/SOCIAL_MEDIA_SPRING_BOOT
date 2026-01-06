package com.kh.chamreunvira.springboot.controller;

import com.kh.chamreunvira.springboot.data.ApiResponse;
import com.kh.chamreunvira.springboot.dto.CommentRequest;
import com.kh.chamreunvira.springboot.model.Comment;
import com.kh.chamreunvira.springboot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Comment>> createComment(@PathVariable Long postId ,@CurrentSecurityContext(expression = "authentication?.name") String email
            , @RequestBody CommentRequest request) {
        return ResponseEntity.ok().body(ApiResponse.success("Comment has been send Successfully." , commentService.create(postId ,email , request)));
    }

    @DeleteMapping("/posts/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/posts/comments/{commentId}")
    public ResponseEntity<ApiResponse<Comment>> updateComment(@PathVariable Long commentId , @RequestBody CommentRequest request) {
        return ResponseEntity.ok().body(ApiResponse.success("Update comment successfully." , commentService.update(commentId , request)));
    }

}
