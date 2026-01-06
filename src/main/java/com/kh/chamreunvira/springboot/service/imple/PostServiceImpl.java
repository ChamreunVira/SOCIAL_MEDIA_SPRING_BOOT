package com.kh.chamreunvira.springboot.service.imple;

import com.kh.chamreunvira.springboot.dto.PostRequest;
import com.kh.chamreunvira.springboot.dto.PostResponse;
import com.kh.chamreunvira.springboot.mapper.PostMapper;
import com.kh.chamreunvira.springboot.model.Post;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.repository.PostRepository;
import com.kh.chamreunvira.springboot.repository.UserRepository;
import com.kh.chamreunvira.springboot.security.AuthService;
import com.kh.chamreunvira.springboot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    @Override
    public Post create(PostRequest req , String email) {
        User user = authService.fetchUserByEmail(email);
        Post post = postMapper.mapperToEntity(req , user.getId());
        postRepository.save(post);
        return post;
    }

    @Override
    public Post update(Long id, PostRequest req) {
        Post existsPost = postRepository.
                findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with: " + id));
        existsPost.setTitle(req.getTitle());
        existsPost.setContent(req.getContent());
        Post savePost = postRepository.save(existsPost);
        return savePost;
    }

    @Override
    public List<PostResponse> getAll(String email) {
        User user = authService.fetchUserByEmail(email);
        List<Post> post = postRepository.findAll();
        return postMapper.mapperToList(post , user.getId());
    }

    @Override
    public PostResponse getById(Long id , String email) {
        User user = authService.fetchUserByEmail(email);
        Post post = postRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with user: " + id));
        return postMapper.mapperToResponse(post , user.getId());
    }

    @Override
    public Post fetchPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with user: " + id));
        postRepository.delete(post);
    }
}
