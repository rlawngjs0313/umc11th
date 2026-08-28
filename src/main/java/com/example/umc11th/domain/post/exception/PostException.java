package com.example.umc11th.domain.post.exception;

import com.example.umc11th.global.apiPayload.exception.CustomException;

public class PostException extends CustomException {

    public PostException(PostErrorCode code) {
        super(code);
    }
}
