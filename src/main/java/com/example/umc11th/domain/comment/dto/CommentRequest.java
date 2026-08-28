package com.example.umc11th.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequest {

    public record Write(
            @NotBlank @Size(max = 1000) String content
    ) {
    }
}
