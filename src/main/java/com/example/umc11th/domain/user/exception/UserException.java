package com.example.umc11th.domain.user.exception;

import com.example.umc11th.global.apiPayload.exception.CustomException;

public class UserException extends CustomException {

    public UserException(UserErrorCode code) {
        super(code);
    }
}
