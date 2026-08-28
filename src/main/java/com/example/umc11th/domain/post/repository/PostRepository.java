package com.example.umc11th.domain.post.repository;

import com.example.umc11th.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "author")
    Optional<Post> findWithAuthorById(Long id);

    @EntityGraph(attributePaths = "author")
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
