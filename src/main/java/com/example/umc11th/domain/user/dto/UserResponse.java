package com.example.umc11th.domain.user.dto;

import com.example.umc11th.domain.user.entity.User;

public class UserResponse {

    public record Token(String accessToken) {
    }

    public record Profile(Long id, String name, String email) {

        public static Profile from(User user) {
            return new Profile(user.getId(), user.getName(), user.getEmail());
        }
    }
}
