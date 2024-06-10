package com.example.project.dto;

import com.example.project.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long postNo;
    private String title;
    private String contents;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.postNo = post.getPostNo();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.userName = post.getAuthor();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
