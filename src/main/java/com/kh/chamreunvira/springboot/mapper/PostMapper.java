package com.kh.chamreunvira.springboot.mapper;

import com.kh.chamreunvira.springboot.dto.*;
import com.kh.chamreunvira.springboot.model.Comment;
import com.kh.chamreunvira.springboot.model.Post;
import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.repository.LikeRepository;
import com.kh.chamreunvira.springboot.security.AuthService;
import com.kh.chamreunvira.springboot.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final AuthService authService;
    private final LikeRepository likeRepository;
    private final FileUtil fileUtil;

    public Post mapperToEntity(PostRequest req , Long userId) throws Exception {
        Post post = new Post();
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        MultipartFile file = req.getFile();
        if(file != null && !file.isEmpty()) {
            String fileName = fileUtil.saveFile(file);
            post.setImage(fileName);
        }
        User user = authService.fetchUserById(userId);
        post.setUser(user);
        return post;
    }

    public List<CommentResponse> mapperToResponseFromComment(List<Comment> comments) {
        return comments
                .stream().map(comment -> CommentResponse
                        .builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .userId(comment.getUser().getId())
                        .username(comment.getUser().getFullName())
                        .createAt(comment.getCreateAt())
                        .build())
                .toList();
    }

    public PostResponse mapperToResponse(Post post , Long userId) {

        PostResponse response = new PostResponse();
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setUserId(post.getUser().getId());
        authorResponse.setUsername(post.getUser().getFullName());
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setImage(post.getImage());
        response.setAuthorResponse(authorResponse);
        List<CommentResponse> commentResponses = mapperToResponseFromComment(post.getComments());
        response.setComments(commentResponses);
        int totalLikes = likeRepository.countByPostId(response.getId());
        boolean likeByMe = likeRepository.existsByPostIdAndUserId(response.getId() , userId);
        response.setLikeCount(totalLikes);
        response.setLikeByMe(likeByMe);
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());

        return response;
    }

    public List<PostResponse> mapperToList(List<Post> posts , Long userId) {
        return posts.stream().map(p -> {
            AuthorResponse authorResponse = new AuthorResponse();
            authorResponse.setUserId(p.getUser().getId());
            authorResponse.setUsername(p.getUser().getFullName());
            int totalLikes = likeRepository.countByPostId(p.getId());
            boolean likeByMe = likeRepository.existsByPostIdAndUserId(p.getId() , userId);
            return PostResponse.builder()
                    .id(p.getId())
                    .title(p.getTitle())
                    .content(p.getContent())
                    .image(p.getImage())
                    .authorResponse(authorResponse)
                    .comments(mapperToResponseFromComment(p.getComments()))
                    .likeCount(totalLikes)
                    .likeByMe(likeByMe)
                    .createdAt(p.getCreatedAt())
                    .updatedAt(p.getUpdatedAt())
                   .build();
        }).toList();
    }


}
