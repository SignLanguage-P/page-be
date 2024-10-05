package com.example.p_project.domain.User.service;

import com.example.p_project.domain.User.dto.request.UserRequestDTO;
import com.example.p_project.domain.User.dto.response.UserResponseDTO;

// 사용자 관련 비즈니스 로직을 정의하는 인터페이스
public interface UserService {
    // 새로운 사용자를 등록
    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);

    // 사용자 이름으로 사용자를 find
    UserResponseDTO findByUsername(String username);

    // 사용자 정보를 update
    UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO);

    // 사용자를 delete
    void deleteUser(Long userId);
}
