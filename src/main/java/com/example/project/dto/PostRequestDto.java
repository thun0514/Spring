package com.example.project.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PostRequestDto {
    private String title;
    private String contents;
}
