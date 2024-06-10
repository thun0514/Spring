package com.example.project.controller;


import com.example.project.dto.LoginRequestDto;
import com.example.project.dto.SignupRequestDto;
import com.example.project.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);

        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("message", "회원가입 완료");
        userResponse.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);

        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("message", "로그인 완료");
        userResponse.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}