package com.example.umc11th.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostRequest {

    public record Write(
            @NotBlank @Size(max = 100) String title,
            @NotBlank @Size(max = 10_000) String content
    ) {
    }
}
