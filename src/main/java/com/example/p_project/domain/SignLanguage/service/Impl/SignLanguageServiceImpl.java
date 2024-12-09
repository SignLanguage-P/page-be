package com.example.p_project.domain.SignLanguage.service.Impl;

import ai.djl.Application;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import com.example.p_project.domain.SignLanguage.dto.response.SignLanguageResponseDTO;
import com.example.p_project.domain.SignLanguage.entity.PredictionHistory;
import com.example.p_project.domain.SignLanguage.repository.PredictionHistoryRepository;
import com.example.p_project.domain.SignLanguage.service.SignLanguageService;
import com.example.p_project.domain.SignLanguage.translator.MyImageTranslator;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignLanguageServiceImpl implements SignLanguageService {
    private final PredictionHistoryRepository predictionHistoryRepository;
    private ZooModel<Image, Classifications> model;
    private Predictor<Image, Classifications> predictor;

    @Value("${model.path}")
    private String modelPath;

    @Value("${model.input.width:224}")
    private int inputWidth;

    @Value("${model.input.height:224}")
    private int inputHeight;

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            // 모델 초기화 로직
            Path modelDir = Paths.get(Optional.ofNullable(getClass().getResource(modelPath))
                    .orElseThrow(() -> new RuntimeException("모델 경로를 찾을 수 없습니다: " + modelPath))
                    .toURI());

            Criteria<Image, Classifications> criteria = Criteria.builder()
                    .optApplication(Application.CV.IMAGE_CLASSIFICATION)
                    .setTypes(Image.class, Classifications.class)
                    .optEngine("TensorFlow")
                    .optModelPath(modelDir)
                    .optProgress(new ProgressBar())
                    .optTranslator(new MyImageTranslator(inputWidth, inputHeight))
                    .build();

            model = ModelZoo.loadModel(criteria);
            predictor = model.newPredictor();
            log.info("수어 인식 모델 로드 완료");
        } catch (Exception e) {
            log.error("모델 초기화 실패: {}", e.getMessage());
            throw new RuntimeException("모델 초기화 실패", e);
        }
    }

    @Override
    @Transactional
    public SignLanguageResponseDTO predictSign(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            validateImage(file);
            String imagePath = saveImage(file);

            Image image = ImageFactory.getInstance().fromInputStream(inputStream);
            Classifications result = predictor.predict(image);
            Classifications.Classification bestResult = result.best();

            SignLanguageResponseDTO response = SignLanguageResponseDTO.builder()
                    .predictedSign(bestResult.getClassName())
                    .confidence(bestResult.getProbability())
                    .processingTime(System.currentTimeMillis())
                    .build();

            savePredictionResult(response, imagePath);
            return response;
        } catch (Exception e) {
            log.error("수어 예측 실패: {}", e.getMessage());
            throw new RuntimeException("수어 예측 실패", e);
        }
    }

    @Override
    public String getModelStatus() {
        return (predictor != null && model != null) ? "Model is running" : "Model is not initialized";
    }

    @Override
    @Transactional(readOnly = true)
    public List<PredictionHistory> getRecentPredictions() {
        return predictionHistoryRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PredictionHistory> getPredictionsBySign(String sign) {
        return predictionHistoryRepository.findByPredictedSignOrderByCreatedAtDesc(sign);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PredictionHistory> getPredictionsByConfidence(Double minConfidence) {
        return predictionHistoryRepository.findByConfidenceGreaterThanEqual(minConfidence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PredictionHistory> getPredictionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return predictionHistoryRepository.findByDateRange(startDate, endDate);
    }

    @Override
    @Transactional
    public PredictionHistory savePredictionResult(SignLanguageResponseDTO result, String imagePath) {
        PredictionHistory history = PredictionHistory.builder()
                .predictedSign(result.getPredictedSign())
                .confidence(result.getConfidence())
                .processingTime(result.getProcessingTime())
                .imagePath(imagePath)
                .build();

        return predictionHistoryRepository.save(history);
    }

    @Override
    @Transactional
    public void updateFeedback(Long predictionId, boolean isCorrect, String feedback) {
        PredictionHistory prediction = predictionHistoryRepository.findById(predictionId)
                .orElseThrow(() -> new RuntimeException("예측 기록을 찾을 수 없습니다: " + predictionId));

        prediction.setIsCorrect(isCorrect);
        prediction.setFeedback(feedback);
        predictionHistoryRepository.save(prediction);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getPredictionStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        long totalPredictions = predictionHistoryRepository.count();
        long correctPredictions = predictionHistoryRepository.countCorrectPredictions();

        statistics.put("totalPredictions", totalPredictions);
        statistics.put("correctPredictions", correctPredictions);
        statistics.put("accuracy", calculateAccuracy(correctPredictions, totalPredictions));

        return statistics;
    }

    private double calculateAccuracy(long correct, long total) {
        return total > 0 ? (double) correct / total * 100 : 0.0;
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("파일이 비어있습니다.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("이미지 파일만 업로드 가능합니다.");
        }

        if (file.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new RuntimeException("파일 크기는 5MB를 초과할 수 없습니다.");
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();
    }

    @PreDestroy
    public void destroy() {
        try {
            if (predictor != null) predictor.close();
            if (model != null) model.close();
        } catch (Exception e) {
            log.error("리소스 해제 실패: {}", e.getMessage());
        }
    }
}
