package com.example.p_project.domain.SignLanguage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "prediction_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String predictedSign;

    @Column(nullable = false)
    private Double confidence;

    @Column(name = "processing_time")
    private Long processingTime;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(length = 500)
    private String feedback;
}
