package com.example.umc11th.global.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtTokenProviderTest {

    @Test
    void createsAndReadsAccessToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(
                "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=",
                3_600_000
        );

        String token = jwtTokenProvider.createToken("user@example.com");

        assertEquals("user@example.com", jwtTokenProvider.getEmail(token));
    }
}
