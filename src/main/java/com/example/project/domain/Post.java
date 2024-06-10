package com.example.project.domain;

import com.example.project.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String author;

    @Builder
    public Post(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    @Builder
    public Post(PostRequestDto requestDto, String author) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.author = author;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }
}
