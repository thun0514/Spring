package com.example.project.dto;

import com.example.project.domain.RoleType;
import lombok.Data;

@Data
public class SignupRequestDto {
    private String userId;
    private String password;
    private String userName;
    private String email;
    private RoleType userRole;
}
