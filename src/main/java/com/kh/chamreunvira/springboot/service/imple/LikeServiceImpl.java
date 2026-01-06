package com.kh.chamreunvira.springboot.service.imple;

import com.kh.chamreunvira.springboot.model.Like;
import com.kh.chamreunvira.springboot.model.Post;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.repository.LikeRepository;
import com.kh.chamreunvira.springboot.security.AuthService;
import com.kh.chamreunvira.springboot.service.LikeService;
import com.kh.chamreunvira.springboot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final AuthService authService;
    private final PostService postService;
    private final LikeRepository likeRepository;

    @Override
    public void toggleLike(Long postId, String email) {
        Like like = new Like();
        User existsUser = authService.fetchUserByEmail(email);
        Long userId = existsUser.getId();
        Optional<Like> existsLike = likeRepository.findByPostIdAndUserId(postId , userId);
        if(existsLike.isPresent()) {
            likeRepository.delete(existsLike.get());
            return;
        }

        Post post = postService.fetchPostById(postId);

        like.setPost(post);
        like.setUser(existsUser);

        likeRepository.save(like);

    }
}
