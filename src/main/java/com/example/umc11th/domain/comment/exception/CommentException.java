package com.example.umc11th.domain.comment.exception;

import com.example.umc11th.global.apiPayload.exception.CustomException;

public class CommentException extends CustomException {

    public CommentException(CommentErrorCode code) {
        super(code);
    }
}
