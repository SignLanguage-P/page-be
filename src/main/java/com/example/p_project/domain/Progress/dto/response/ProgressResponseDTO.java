package com.example.p_project.domain.Progress.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 진행 상태(Progress) 조회 결과를 위한 DTO 클래스
 * 사용자의 단어 학습 진행 상태 정보를 클라이언트에게 전달하는데 사용됩니다.
 * 사용자 정보와 단어 정보를 포함한 상세 진행 상태를 제공합니다.
 */
@Getter             // 모든 필드의 getter 메서드 자동 생성
@Setter             // 모든 필드의 setter 메서드 자동 생성
@NoArgsConstructor  // 매개변수가 없는 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
public class ProgressResponseDTO {
    /**
     * 진행 상태의 고유 식별자
     * 데이터베이스에서 해당 진행 상태를 식별하는데 사용됩니다.
     */
    private Long id;

    /**
     * 진행 상태의 소유자인 사용자의 고유 식별자
     * 해당 진행 상태가 어느 사용자의 것인지 식별하는데 사용됩니다.
     */
    private Long userId;

    /**
     * 사용자의 이름 또는 닉네임
     * 클라이언트 측에서 사용자를 표시하는데 사용됩니다.
     */
    private String username;

    /**
     * 학습 대상 단어의 고유 식별자
     * 사용자가 학습 중인 특정 단어를 식별하는데 사용됩니다.
     */
    private Long wordId;

    /**
     * 학습 대상 단어의 실제 내용
     * 클라이언트 측에서 단어를 표시하는데 사용됩니다.
     */
    private String wordContent;

    /**
     * 단어 학습 완료 여부
     * true: 학습 완료
     * false: 학습 미완료
     */
    private boolean completed;

    /**
     * 마지막 학습 시간
     * 사용자가 해당 단어를 마지막으로 학습한 날짜와 시간을 저장합니다.
     * ISO-8601 형식으로 표현됩니다.
     */
    private LocalDateTime lastStudiedAt;

    /**
     * 학습 시도 횟수
     * 사용자가 해당 단어를 학습한 총 횟수를 나타냅니다.
     * 학습 진도와 반복 학습 현황을 파악하는데 사용됩니다.
     */
    private int studyCount;
}
