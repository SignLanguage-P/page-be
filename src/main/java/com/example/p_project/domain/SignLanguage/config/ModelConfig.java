package com.example.p_project.domain.SignLanguage.config;

import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import com.example.p_project.domain.SignLanguage.translator.MyImageTranslator;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.file.Path;

@Configuration
@Slf4j
public class ModelConfig {
    @Value("${model.path}")
    private String modelPath;

    @Value("${model.input.width:224}")
    private int inputWidth;

    @Value("${model.input.height:224}")
    private int inputHeight;

    @Bean
    public ZooModel<Image, Classifications> signLanguageModel() throws ModelException, IOException {
        String path = modelPath.replace("classpath:", "");
        Resource resource = new ClassPathResource(path);
        Path modelPath = resource.getFile().toPath();

        Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optEngine("TensorFlow")
                .optModelPath(modelPath)
                .optOption("Tags", "serve")
                .optTranslator(new MyImageTranslator(inputWidth, inputHeight))
                .build();

        try {
            ZooModel<Image, Classifications> model = criteria.loadModel();
            log.info("수어 인식 모델 로드 완료: {}", modelPath);
            return model;
        } catch (ModelException | IOException e) {
            log.error("모델 로드 실패: {}", e.getMessage());
            throw e;
        }
    }

    @Bean
    public Predictor<Image, Classifications> signLanguagePredictor(
            ZooModel<Image, Classifications> model) {
        return model.newPredictor();
    }

    @Bean
    public ModelProperties modelProperties() {
        return ModelProperties.builder()
                .inputWidth(inputWidth)
                .inputHeight(inputHeight)
                .modelPath(modelPath)
                .build();
    }
}

@Getter
@Builder
class ModelProperties {
    private final int inputWidth;
    private final int inputHeight;
    private final String modelPath;
}