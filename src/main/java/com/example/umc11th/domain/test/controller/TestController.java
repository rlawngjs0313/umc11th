package com.example.umc11th.domain.test.controller;

import com.example.umc11th.domain.test.exception.TestException;
import com.example.umc11th.global.apiPayload.CustomResponse;
import com.example.umc11th.global.apiPayload.code.GeneralErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public CustomResponse<String> test() {
        return CustomResponse.ok("test");
    }

    @GetMapping("/exception")
    public CustomResponse<String> exception() {
        throw new TestException(GeneralErrorCode.VALIDATION_FAILED);
    }
}
