package com.example.p_project.domain.User.controller;

import com.example.p_project.domain.User.dto.request.UserRequestDTO;
import com.example.p_project.domain.User.dto.response.UserResponseDTO;
import com.example.p_project.domain.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // RESTful 웹 서비스의 컨트롤러
@RequestMapping("/api/users") // 이 컨트롤러의 기본 URL 경로를 지정
public class UserController {
    @Autowired
    private UserService userService;

    // 새로운 사용자를 등록하는 엔드포인트
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.registerUser(userRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 사용자 이름으로 사용자 정보를 조회하는 엔드포인트
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String username) {
        UserResponseDTO responseDTO = userService.findByUsername(username);
        return responseDTO != null ? ResponseEntity.ok(responseDTO) : ResponseEntity.notFound().build();
    }

    // 사용자 정보를 업데이트하는 엔드포인트
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.updateUser(userId, userRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 사용자를 삭제하는 엔드포인트
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
