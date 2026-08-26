package com.example.umc11th.domain.user.service;

import com.example.umc11th.domain.user.dto.UserRequest;
import com.example.umc11th.domain.user.dto.UserResponse;
import com.example.umc11th.domain.user.entity.User;
import com.example.umc11th.domain.user.exception.UserErrorCode;
import com.example.umc11th.domain.user.exception.UserException;
import com.example.umc11th.domain.user.repository.UserRepository;
import com.example.umc11th.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserResponse.Profile signUp(UserRequest.SignUp request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserException(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = userRepository.save(User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build());
        return UserResponse.Profile.from(user);
    }

    public UserResponse.Token login(UserRequest.Login request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserException(UserErrorCode.INVALID_CREDENTIALS));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_CREDENTIALS);
        }
        return new UserResponse.Token(jwtTokenProvider.createToken(user.getEmail()));
    }

    public UserResponse.Profile getMe(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return UserResponse.Profile.from(user);
    }
}
