package com.example.umc11th.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {

    public record SignUp(
            @NotBlank String name,
            @Email @NotBlank String email,
            @Size(min = 8, max = 72) String password
    ) {
    }

    public record Login(
            @Email @NotBlank String email,
            @NotBlank String password
    ) {
    }
}
