package com.example.umc11th.domain.comment.controller;

import com.example.umc11th.domain.comment.dto.CommentRequest;
import com.example.umc11th.domain.comment.dto.CommentResponse;
import com.example.umc11th.domain.comment.service.CommentService;
import com.example.umc11th.global.apiPayload.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public CustomResponse<CommentResponse> create(
            @AuthenticationPrincipal String email,
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest.Write request
    ) {
        return CustomResponse.created(commentService.create(email, postId, request));
    }

    @GetMapping("/posts/{postId}/comments")
    public CustomResponse<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return CustomResponse.ok(commentService.getComments(postId));
    }

    @PutMapping("/comments/{commentId}")
    public CustomResponse<CommentResponse> update(
            @AuthenticationPrincipal String email,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest.Write request
    ) {
        return CustomResponse.ok(commentService.update(email, commentId, request));
    }

    @DeleteMapping("/comments/{commentId}")
    public CustomResponse<Void> delete(
            @AuthenticationPrincipal String email,
            @PathVariable Long commentId
    ) {
        commentService.delete(email, commentId);
        return CustomResponse.ok(null);
    }
}
