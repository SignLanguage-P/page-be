package com.example.p_project.domain.Word.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordResponseDTO {
    private Long id;
    private String content;
    private String description;
    private String videoUrl;
    private String categoryName;
}
