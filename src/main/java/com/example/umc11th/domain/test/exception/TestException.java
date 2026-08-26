package com.example.umc11th.domain.test.exception;

import com.example.umc11th.global.apiPayload.code.BaseErrorCode;
import com.example.umc11th.global.apiPayload.exception.CustomException;

public class TestException extends CustomException {
    public TestException(BaseErrorCode code) {
        super(code);
    }
}
