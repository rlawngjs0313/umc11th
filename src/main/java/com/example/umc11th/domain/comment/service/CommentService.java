package com.example.umc11th.domain.comment.service;

import com.example.umc11th.domain.comment.dto.CommentRequest;
import com.example.umc11th.domain.comment.dto.CommentResponse;
import com.example.umc11th.domain.comment.entity.Comment;
import com.example.umc11th.domain.comment.exception.CommentErrorCode;
import com.example.umc11th.domain.comment.exception.CommentException;
import com.example.umc11th.domain.comment.repository.CommentRepository;
import com.example.umc11th.domain.post.entity.Post;
import com.example.umc11th.domain.post.exception.PostErrorCode;
import com.example.umc11th.domain.post.exception.PostException;
import com.example.umc11th.domain.post.repository.PostRepository;
import com.example.umc11th.domain.user.entity.User;
import com.example.umc11th.domain.user.exception.UserErrorCode;
import com.example.umc11th.domain.user.exception.UserException;
import com.example.umc11th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse create(String email, Long postId, CommentRequest.Write request) {
        Comment comment = commentRepository.save(Comment.builder()
                .content(request.content())
                .post(getPost(postId))
                .author(getUser(email))
                .build());
        return CommentResponse.from(comment);
    }

    public List<CommentResponse> getComments(Long postId) {
        getPost(postId);
        return commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId).stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public CommentResponse update(String email, Long commentId, CommentRequest.Write request) {
        Comment comment = getCommentWithAuthor(commentId);
        validateAuthor(comment, getUser(email));
        comment.update(request.content());
        return CommentResponse.from(comment);
    }

    @Transactional
    public void delete(String email, Long commentId) {
        Comment comment = getCommentWithAuthor(commentId);
        validateAuthor(comment, getUser(email));
        commentRepository.delete(comment);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    private Comment getCommentWithAuthor(Long commentId) {
        return commentRepository.findWithAuthorById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }

    private void validateAuthor(Comment comment, User user) {
        if (!comment.isWrittenBy(user)) {
            throw new CommentException(CommentErrorCode.COMMENT_FORBIDDEN);
        }
    }
}
