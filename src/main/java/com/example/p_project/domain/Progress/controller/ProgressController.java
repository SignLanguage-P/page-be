package com.example.p_project.domain.Progress.controller;

import com.example.p_project.domain.Progress.dto.request.ProgressRequestDTO;
import com.example.p_project.domain.Progress.dto.response.ProgressResponseDTO;
import com.example.p_project.domain.Progress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {
    private final ProgressService progressService;

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public ResponseEntity<ProgressResponseDTO> createOrUpdateProgress(@RequestBody ProgressRequestDTO progressRequestDTO) {
        ProgressResponseDTO progress = progressService.createOrUpdateProgress(progressRequestDTO);
        return new ResponseEntity<>(progress, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressResponseDTO> getProgress(@PathVariable Long id) {
        ProgressResponseDTO progress = progressService.getProgress(id);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressResponseDTO>> getProgressByUser(@PathVariable Long userId) {
        List<ProgressResponseDTO> progress = progressService.getProgressByUser(userId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<ProgressResponseDTO>> getCompletedProgressByUser(@PathVariable Long userId) {
        List<ProgressResponseDTO> progress = progressService.getCompletedProgressByUser(userId);
        return ResponseEntity.ok(progress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}
