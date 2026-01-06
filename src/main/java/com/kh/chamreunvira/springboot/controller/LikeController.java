package com.kh.chamreunvira.springboot.controller;

import com.kh.chamreunvira.springboot.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    public final LikeService likeService;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<Void> handleLike(@PathVariable Long postId , @CurrentSecurityContext(expression = "authentication?.name") String email) {
        likeService.toggleLike(postId , email);
        return ResponseEntity.ok().build();
    }

}
