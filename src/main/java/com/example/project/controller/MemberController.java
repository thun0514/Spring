package com.example.project.controller;


import com.example.project.dto.LoginRequestDto;
import com.example.project.dto.SignupRequestDto;
import com.example.project.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);
        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return "success";
    }
}