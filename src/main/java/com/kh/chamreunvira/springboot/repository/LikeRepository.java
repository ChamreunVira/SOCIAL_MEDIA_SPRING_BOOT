package com.kh.chamreunvira.springboot.repository;

import com.kh.chamreunvira.springboot.model.Like;
import com.kh.chamreunvira.springboot.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByPostIdAndUserId(Long postId , Long userId);
    int countByPostId(Long postId);
    boolean existsByPostIdAndUserId(Long postId , Long userId);
}
