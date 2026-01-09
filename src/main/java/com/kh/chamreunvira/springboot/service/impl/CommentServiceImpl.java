package com.kh.chamreunvira.springboot.service.impl;

import com.kh.chamreunvira.springboot.dto.CommentRequest;
import com.kh.chamreunvira.springboot.model.Comment;
import com.kh.chamreunvira.springboot.model.Post;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.repository.CommentRepository;
import com.kh.chamreunvira.springboot.security.AuthService;
import com.kh.chamreunvira.springboot.service.CommentService;
import com.kh.chamreunvira.springboot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final AuthService authService;

    public Comment create(Long postId , CommentRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        Post post = postService.fetchPostById(postId);
        comment.setPost(post);
        User user = authService.getCurrentUser();
        comment.setUser(user);
        Comment saveComment = commentRepository.save(comment);
        return saveComment;
    }

    public Comment update(Long commentId , CommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with Id : " + commentId));
        comment.setContent(request.getContent());
        Comment saveComment = commentRepository.save(comment);
        return saveComment;
    }

    public void delete(Long commentId) {
        Comment comment = commentRepository
                .findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }


}
