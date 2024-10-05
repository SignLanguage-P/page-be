package com.example.p_project.domain.User.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 사용자 정보 응답에 사용되는 DTO
public class UserResponseDTO {
    Long id;
    String username;
    String email;
    String role;
    Integer progressLevel;
}
