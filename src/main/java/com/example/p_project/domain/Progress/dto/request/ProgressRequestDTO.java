package com.example.p_project.domain.Progress.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressRequestDTO {
    private Long userId;
    private Long wordId;
    private boolean completed;
}
