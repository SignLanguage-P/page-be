package com.example.p_project.domain.SignLanguage.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class SignLanguageRequestDTO {
    private MultipartFile image;

    public boolean isValid() {
        return image != null && !image.isEmpty();
    }
}
