package com.example.p_project.domain.Progress.service;

import com.example.p_project.domain.Progress.dto.request.ProgressRequestDTO;
import com.example.p_project.domain.Progress.dto.response.ProgressResponseDTO;

import java.util.List;

public interface ProgressService {
    ProgressResponseDTO createOrUpdateProgress(ProgressRequestDTO progressRequestDTO);
    ProgressResponseDTO getProgress(Long id);
    List<ProgressResponseDTO> getProgressByUser(Long userId);
    List<ProgressResponseDTO> getCompletedProgressByUser(Long userId);
    void deleteProgress(Long id);
}
