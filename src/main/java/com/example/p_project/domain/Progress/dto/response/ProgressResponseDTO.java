package com.example.p_project.domain.Progress.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressResponseDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long wordId;
    private String wordContent;
    private boolean completed;
    private LocalDateTime lastStudiedAt;
    private int studyCount;
}
