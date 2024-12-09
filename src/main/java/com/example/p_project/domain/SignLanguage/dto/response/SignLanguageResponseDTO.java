package com.example.p_project.domain.SignLanguage.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignLanguageResponseDTO {
    private String predictedSign;
    private Double confidence;
    private Long processingTime;
    private String errorMessage;
    private String imagePath;

    public static SignLanguageResponseDTO success(String sign, Double confidence, Long time, String imagePath) {
        return SignLanguageResponseDTO.builder()
                .predictedSign(sign)
                .confidence(confidence)
                .processingTime(time)
                .imagePath(imagePath)
                .build();
    }

    public static SignLanguageResponseDTO error(String message) {
        return SignLanguageResponseDTO.builder()
                .errorMessage(message)
                .build();
    }
}
