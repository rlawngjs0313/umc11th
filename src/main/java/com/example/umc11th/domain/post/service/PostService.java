package com.example.umc11th.domain.post.service;

import com.example.umc11th.domain.post.dto.PostRequest;
import com.example.umc11th.domain.post.dto.PostResponse;
import com.example.umc11th.domain.post.entity.Post;
import com.example.umc11th.domain.post.exception.PostErrorCode;
import com.example.umc11th.domain.post.exception.PostException;
import com.example.umc11th.domain.post.repository.PostRepository;
import com.example.umc11th.domain.user.entity.User;
import com.example.umc11th.domain.user.exception.UserErrorCode;
import com.example.umc11th.domain.user.exception.UserException;
import com.example.umc11th.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse create(String email, PostRequest.Write request) {
        User author = getUser(email);
        Post post = postRepository.save(Post.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .build());
        return PostResponse.from(post);
    }

    public Page<PostResponse> getPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostResponse::from);
    }

    public PostResponse getPost(Long postId) {
        return PostResponse.from(getPostWithAuthor(postId));
    }

    @Transactional
    public PostResponse update(String email, Long postId, PostRequest.Write request) {
        Post post = getPostWithAuthor(postId);
        validateAuthor(post, getUser(email));
        post.update(request.title(), request.content());
        return PostResponse.from(post);
    }

    @Transactional
    public void delete(String email, Long postId) {
        Post post = getPostWithAuthor(postId);
        validateAuthor(post, getUser(email));
        postRepository.delete(post);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    private Post getPostWithAuthor(Long postId) {
        return postRepository.findWithAuthorById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    private void validateAuthor(Post post, User user) {
        if (!post.isWrittenBy(user)) {
            throw new PostException(PostErrorCode.POST_FORBIDDEN);
        }
    }
}
