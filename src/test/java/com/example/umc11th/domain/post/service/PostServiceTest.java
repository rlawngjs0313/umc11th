package com.example.umc11th.domain.post.service;

import com.example.umc11th.domain.post.dto.PostRequest;
import com.example.umc11th.domain.post.entity.Post;
import com.example.umc11th.domain.post.exception.PostException;
import com.example.umc11th.domain.post.repository.PostRepository;
import com.example.umc11th.domain.user.entity.User;
import com.example.umc11th.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void preventsAnotherUserFromUpdatingAPost() {
        User author = User.builder().id(1L).build();
        User anotherUser = User.builder().id(2L).email("another@example.com").build();
        Post post = Post.builder().id(1L).author(author).build();
        when(postRepository.findWithAuthorById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findByEmail("another@example.com")).thenReturn(Optional.of(anotherUser));

        assertThrows(PostException.class, () -> postService.update(
                "another@example.com",
                1L,
                new PostRequest.Write("수정", "수정 내용")
        ));
    }
}
