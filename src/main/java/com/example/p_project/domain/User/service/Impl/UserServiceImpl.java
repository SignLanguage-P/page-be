package com.example.p_project.domain.User.service.Impl;

import com.example.p_project.domain.User.dto.request.UserRequestDTO;
import com.example.p_project.domain.User.dto.response.UserResponseDTO;
import com.example.p_project.domain.User.entity.User;
import com.example.p_project.domain.User.repository.UserRepository;
import com.example.p_project.domain.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 사용자 서비스의 구현 클래스입니다.
 * 사용자와 관련된 비즈니스 로직을 처리합니다.
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 사용자 데이터 접근을 위한 리포지토리입니다.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * 비밀번호 암호화를 위한 인코더입니다.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param userRequestDTO 사용자 등록에 필요한 정보를 담은 DTO
     * @return 등록된 사용자의 정보를 담은 ResponseDTO
     */
    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        // 비밀번호를 암호화하여 저장
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        // 기본 사용자 역할 설정
        user.setRole("ROLE_USER");
        // 초기 진행 수준 설정
        user.setProgressLevel(0);

        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }

    /**
     * 사용자명으로 사용자를 조회합니다.
     *
     * @param username 조회할 사용자의 사용자명
     * @return 조회된 사용자 정보를 담은 ResponseDTO, 없을 경우 null
     */
    @Override
    public UserResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? convertToResponseDTO(user) : null;
    }

    /**
     * 기존 사용자 정보를 수정합니다.
     *
     * @param userId 수정할 사용자의 ID
     * @param userRequestDTO 수정할 사용자 정보를 담은 DTO
     * @return 수정된 사용자 정보를 담은 ResponseDTO
     * @throws RuntimeException 사용자를 찾을 수 없는 경우
     */
    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 기본 정보 업데이트
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());

        // 비밀번호가 제공된 경우에만 업데이트
        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return convertToResponseDTO(updatedUser);
    }

    /**
     * 사용자를 삭제합니다.
     *
     * @param userId 삭제할 사용자의 ID
     */
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * User 엔티티를 UserResponseDTO로 변환합니다.
     * 비밀번호와 같은 민감한 정보는 제외하고 변환합니다.
     *
     * @param user 변환할 User 엔티티
     * @return 변환된 UserResponseDTO
     */
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
