package com.kh.chamreunvira.springboot.repository;

import com.kh.chamreunvira.springboot.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post , Long> {
}
