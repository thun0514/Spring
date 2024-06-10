package com.example.project.service;


import com.example.project.domain.Member;
import com.example.project.dto.LoginRequestDto;
import com.example.project.dto.SignupRequestDto;
import com.example.project.repository.MemberRepository;
import com.example.project.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String userId = signupRequestDto.getUserId();
        String password = signupRequestDto.getPassword();
        String email = signupRequestDto.getEmail();
        String userName = signupRequestDto.getUserName();

        Optional<Member> foundId = memberRepository.findByUserId(userId);
        Optional<Member> foundEmail = memberRepository.findByEmail(email);

        if (foundId.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 ID 입니다.");
        }

        if (foundEmail.isPresent()) {
            throw new IllegalArgumentException("이미 가입된 E-Mail 입니다.");
        }

        Member member = new Member(userId, password, userName, email);
        memberRepository.save(member);
    }

    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUserId(), member.getUserName(), member.getUserRole()));
    }
}
