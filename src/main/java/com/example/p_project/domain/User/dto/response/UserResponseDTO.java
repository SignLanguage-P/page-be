package com.example.p_project.domain.User.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 사용자 정보 응답에 사용되는 DTO
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Integer progressLevel;
}
