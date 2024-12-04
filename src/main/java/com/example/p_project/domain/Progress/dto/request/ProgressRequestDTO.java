package com.example.p_project.domain.Progress.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 진행 상태(Progress) 생성 및 수정 요청을 위한 DTO 클래스
 * 사용자의 단어 학습 진행 상태 정보를 전달하는데 사용됩니다.
 */
@Getter             // 모든 필드의 getter 메서드 자동 생성
@Setter             // 모든 필드의 setter 메서드 자동 생성
@NoArgsConstructor  // 매개변수가 없는 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
public class ProgressRequestDTO {
    /**
     * 진행 상태의 소유자인 사용자의 고유 식별자
     * 해당 진행 상태가 어느 사용자의 것인지 식별하는데 사용됩니다.
     */
    private Long userId;

    /**
     * 학습 대상 단어의 고유 식별자
     * 사용자가 학습 중인 특정 단어를 식별하는데 사용됩니다.
     */
    private Long wordId;

    /**
     * 단어 학습 완료 여부
     * true: 학습 완료
     * false: 학습 미완료
     */
    private boolean completed;
}
