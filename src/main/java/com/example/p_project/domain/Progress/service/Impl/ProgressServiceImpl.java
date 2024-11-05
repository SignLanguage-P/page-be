package com.example.p_project.domain.Progress.service.Impl;

import com.example.p_project.domain.Progress.dto.request.ProgressRequestDTO;
import com.example.p_project.domain.Progress.dto.response.ProgressResponseDTO;
import com.example.p_project.domain.Progress.entity.Progress;
import com.example.p_project.domain.Progress.repository.ProgressRepository;
import com.example.p_project.domain.Progress.service.ProgressService;
import com.example.p_project.domain.User.repository.UserRepository;
import com.example.p_project.domain.Word.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressServiceImpl implements ProgressService {
    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;

    @Autowired
    public ProgressServiceImpl(ProgressRepository progressRepository, UserRepository userRepository, WordRepository wordRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public ProgressResponseDTO createOrUpdateProgress(ProgressRequestDTO progressRequestDTO) {
        Progress progress = progressRepository.findByUserIdAndWordId(progressRequestDTO.getUserId(), progressRequestDTO.getWordId())
                .orElse(new Progress());

        progress.setUser(userRepository.findById(progressRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        progress.setWord(wordRepository.findById(progressRequestDTO.getWordId())
                .orElseThrow(() -> new RuntimeException("Word not found")));
        progress.setCompleted(progressRequestDTO.isCompleted());
        progress.setLastStudiedAt(LocalDateTime.now());
        progress.setStudyCount(progress.getStudyCount() + 1);

        Progress savedProgress = progressRepository.save(progress);
        return convertToResponseDTO(savedProgress);
    }

    @Override
    public ProgressResponseDTO getProgress(Long id) {
        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progress not found"));
        return convertToResponseDTO(progress);
    }

    @Override
    public List<ProgressResponseDTO> getProgressByUser(Long userId) {
        return progressRepository.findByUserId(userId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressResponseDTO> getCompletedProgressByUser(Long userId) {
        return progressRepository.findByUserIdAndCompleted(userId, true).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProgress(Long id) {
        progressRepository.deleteById(id);
    }

    private ProgressResponseDTO convertToResponseDTO(Progress progress) {
        return new ProgressResponseDTO(
                progress.getId(),
                progress.getUser().getId(),
                progress.getUser().getUsername(),
                progress.getWord().getId(),
                progress.getWord().getContent(),
                progress.isCompleted(),
                progress.getLastStudiedAt(),
                progress.getStudyCount()
        );
    }
}
