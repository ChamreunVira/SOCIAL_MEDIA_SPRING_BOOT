package com.kh.chamreunvira.springboot.repository;

import com.kh.chamreunvira.springboot.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment , Long> {
}
