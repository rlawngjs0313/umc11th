package com.example.umc11th.domain.comment.repository;

import com.example.umc11th.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = "author")
    List<Comment> findAllByPostIdOrderByCreatedAtAsc(Long postId);

    @EntityGraph(attributePaths = "author")
    Optional<Comment> findWithAuthorById(Long id);
}
