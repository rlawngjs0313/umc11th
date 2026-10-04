package com.example.umc11th.domain.post.dto;

import com.example.umc11th.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long authorId,
        String authorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getId(),
                post.getAuthor().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
