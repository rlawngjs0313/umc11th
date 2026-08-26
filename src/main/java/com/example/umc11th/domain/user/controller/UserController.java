package com.example.umc11th.domain.user.controller;

import com.example.umc11th.domain.user.dto.UserRequest;
import com.example.umc11th.domain.user.dto.UserResponse;
import com.example.umc11th.domain.user.service.UserService;
import com.example.umc11th.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/auth/signup")
    public CustomResponse<UserResponse.Profile> signUp(@Valid @RequestBody UserRequest.SignUp request) {
        return CustomResponse.created(userService.signUp(request));
    }

    @PostMapping("/v1/auth/login")
    public CustomResponse<UserResponse.Token> login(@Valid @RequestBody UserRequest.Login request) {
        return CustomResponse.ok(userService.login(request));
    }

    @GetMapping("/v1/users/me")
    public CustomResponse<UserResponse.Profile> getMe(@AuthenticationPrincipal String email) {
        return CustomResponse.ok(userService.getMe(email));
    }
}
