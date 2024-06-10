package com.example.project.controller;

import com.example.project.dto.PostRequestDto;
import com.example.project.dto.PostResponseDto;
import com.example.project.security.JwtUtil;
import com.example.project.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;

    // 전체 게시글 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    // 선택한 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("id") Long id, HttpServletRequest servletRequest) {
        PostResponseDto responseDto = postService.getPostById(id, servletRequest);
        return ResponseEntity.ok(responseDto);
    }

    // 게시글 업로드
    @PostMapping("/update")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest servletRequest) {
        PostResponseDto responseDto = postService.createPost(requestDto, servletRequest);
        return ResponseEntity.ok(responseDto);
    }

    // 선택한 게시글 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updatePost(@PathVariable("id") Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest servletRequest) {
        Long updatePostId = postService.update(id, requestDto, servletRequest);
        return ResponseEntity.ok(updatePostId);
    }

    // 선택한 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable("id") Long id, HttpServletRequest servletRequest) {
        postService.deletePost(id, servletRequest);

        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
