package com.example.p_project.domain.User.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 사용자 등록 및 수정 요청에 사용되는 DTO
public class UserRequestDTO {
    String username;
    String email;
    String password;
}
