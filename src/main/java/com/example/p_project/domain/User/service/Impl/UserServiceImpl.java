package com.example.p_project.domain.User.service.Impl;

import com.example.p_project.domain.User.dto.request.UserRequestDTO;
import com.example.p_project.domain.User.dto.response.UserResponseDTO;
import com.example.p_project.domain.User.entity.User;
import com.example.p_project.domain.User.repository.UserRepository;
import com.example.p_project.domain.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // 이 클래스가 서비스 빈임을 나타냄
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        // 비밀번호를 암호화하여 저장
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole("ROLE_USER"); // 기본 역할을 설정
        user.setProgressLevel(0); // 초기 진행 수준을 설정

        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? convertToResponseDTO(user) : null;
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return convertToResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // User 엔티티를 UserResponseDTO로 변환하는 private 메서드
    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setProgressLevel(user.getProgressLevel());
        return dto;
    }

}
