package com.example.project.controller;

import com.example.project.domain.Post;
import com.example.project.dto.PostRequestDto;
import com.example.project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @PostMapping("/posts/update")
    public Post creatPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    @PutMapping("/posts/update")
    public Long updatePost(@RequestParam("id") Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }
}
