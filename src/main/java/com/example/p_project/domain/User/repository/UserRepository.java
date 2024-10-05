package com.example.p_project.domain.User.repository;

import com.example.p_project.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 이 인터페이스가 리포지토리 빈
public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이름으로 사용자를 찾는 메서드
    User findByUsername(String username);

    // 이메일로 사용자를 찾는 메서드
    User findByEmail(String email);

    // 사용자 이름의 존재 여부를 확인하는 메서드
    boolean existsByUsername(String username);

    // 이메일의 존재 여부를 확인하는 메서드
    boolean existsByEmail(String email);
}
