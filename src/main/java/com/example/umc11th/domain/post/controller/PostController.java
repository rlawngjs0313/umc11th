package com.example.umc11th.domain.post.controller;

import com.example.umc11th.domain.post.dto.PostRequest;
import com.example.umc11th.domain.post.dto.PostResponse;
import com.example.umc11th.domain.post.service.PostService;
import com.example.umc11th.global.apiPayload.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public CustomResponse<PostResponse> create(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody PostRequest.Write request
    ) {
        return CustomResponse.created(postService.create(email, request));
    }

    @GetMapping
    public CustomResponse<Page<PostResponse>> getPosts(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return CustomResponse.ok(postService.getPosts(pageable));
    }

    @GetMapping("/{postId}")
    public CustomResponse<PostResponse> getPost(@PathVariable Long postId) {
        return CustomResponse.ok(postService.getPost(postId));
    }

    @PutMapping("/{postId}")
    public CustomResponse<PostResponse> update(
            @AuthenticationPrincipal String email,
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest.Write request
    ) {
        return CustomResponse.ok(postService.update(email, postId, request));
    }

    @DeleteMapping("/{postId}")
    public CustomResponse<Void> delete(
            @AuthenticationPrincipal String email,
            @PathVariable Long postId
    ) {
        postService.delete(email, postId);
        return CustomResponse.ok(null);
    }
}
