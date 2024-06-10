package com.example.project.service;


import com.example.project.domain.Post;
import com.example.project.domain.RoleType;
import com.example.project.dto.PostRequestDto;
import com.example.project.dto.PostResponseDto;
import com.example.project.repository.MemberRepository;
import com.example.project.repository.PostRepository;
import com.example.project.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    // 선택한 게시글 조회
    public PostResponseDto getPostById(Long id, HttpServletRequest token) {
        String authorization = jwtUtil.resolveToken(token);
        if (jwtUtil.validateToken(authorization)) {
            return postRepository.findById(id).map(PostResponseDto::new).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        }
        throw new IllegalArgumentException("올바른 토큰이 아닙니다.");
    }

    // 게시글 업로드
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest token) {
        String authorization = jwtUtil.resolveToken(token);
        if (jwtUtil.validateToken(authorization)) {
            Claims claims = jwtUtil.getUserInfoFromToken(authorization);
            String userId = claims.getSubject();
            Post post = new Post(requestDto, userId);
            postRepository.save(post);
            return new PostResponseDto(post);
        }
        throw new IllegalArgumentException("올바른 토큰이 아닙니다.");
    }

    // 선택한 게시글 수정
    @Transactional
    public Long update(Long id, PostRequestDto requestDto, HttpServletRequest token) {
        Claims claims;
        String userId;
        RoleType userRole;
        String authorization = jwtUtil.resolveToken(token);
        if (authorization != null) {
            if (jwtUtil.validateToken(authorization)) {
                claims = jwtUtil.getUserInfoFromToken(authorization);
                userId = claims.getSubject();
                userRole = RoleType.valueOf(claims.get(jwtUtil.AUTHORIZATION_KEY, String.class));
            } else {
                throw new IllegalArgumentException("올바른 토큰이 아닙니다.");
            }
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            if (userId.equals(post.getAuthor()) || userRole == RoleType.ADMIN) {
                post.update(requestDto);
                return post.getPostNo();
            } else {
                throw new IllegalArgumentException("권한이 없습니다");
            }
        } else {
            return null;
        }
    }

    // 선택한 게시글 삭제
    @Transactional
    public Long deletePost(Long id, HttpServletRequest token) {
        Claims claims;
        String userId;
        RoleType userRole;
        String authorization = jwtUtil.resolveToken(token);
        if (authorization != null) {
            if (jwtUtil.validateToken(authorization)) {
                claims = jwtUtil.getUserInfoFromToken(authorization);
                userId = claims.getSubject();
                userRole = RoleType.valueOf(claims.get(jwtUtil.AUTHORIZATION_KEY, String.class));
            } else {
                throw new IllegalArgumentException("올바른 토큰이 아닙니다.");
            }
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            if (userId.equals(post.getAuthor()) || userRole == RoleType.ADMIN) {
                postRepository.deleteById(id);
                return id;
            } else {
                throw new IllegalArgumentException("권한이 없습니다");
            }
        } else {
            return null;
        }
    }
}
