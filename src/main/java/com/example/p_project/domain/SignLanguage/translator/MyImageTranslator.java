package com.example.p_project.domain.SignLanguage.translator;


import ai.djl.Model;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.Batchifier;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.util.Utils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyImageTranslator implements Translator<Image, Classifications> {
    private final int width;
    private final int height;
    private List<String> synset;

    // 기본 한글 수어 클래스 정의 (synset 파일이 없을 경우 사용)
    private static final List<String> DEFAULT_CLASSES = List.of(
            "ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ",
            "ㅋ", "ㅌ", "ㅍ", "ㅎ", "ㅏ", "ㅑ", "ㅓ", "ㅕ", "ㅗ", "ㅛ",
            "ㅜ", "ㅠ", "ㅡ", "ㅣ"
    );

    public MyImageTranslator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public NDList processInput(TranslatorContext ctx, Image input) throws Exception {
        NDManager manager = ctx.getNDManager();
        NDArray array = input.toNDArray(manager);

        // 이미지 전처리
        array = NDImageUtils.resize(array, height, width);
        array = NDImageUtils.toTensor(array); // ToTensor 변환 추가
        array = array.expandDims(0);

        return new NDList(array);
    }

    @Override
    public Classifications processOutput(TranslatorContext ctx, NDList list) throws Exception {
        NDArray probabilities = list.singletonOrThrow();
        probabilities = probabilities.softmax(1);
        probabilities = probabilities.squeeze(0);

        ArrayList<Double> probList = new ArrayList<>();
        ArrayList<String> classNames = new ArrayList<>();

        long size = probabilities.size();
        // synset이 없을 경우 DEFAULT_CLASSES 사용
        List<String> classes = (synset != null && !synset.isEmpty()) ? synset : DEFAULT_CLASSES;

        for (int i = 0; i < size; i++) {
            String className = i < classes.size() ? classes.get(i) : String.valueOf(i);
            double probability = probabilities.getFloat(i);

            // 일정 확률(예: 1%) 이상인 경우만 결과에 포함
            if (probability > 0.01) {
                probList.add(probability);
                classNames.add(className);
            }
        }

        return new Classifications(classNames, probList);
    }

    @Override
    public Batchifier getBatchifier() {
        return Batchifier.STACK;
    }

    @Override
    public void prepare(TranslatorContext ctx) throws IOException {
        Model model = ctx.getModel();

        // synset.txt 파일 로드 시도
        try {
            if (model.getProperty("synset") != null) {
                try (InputStream is = model.getArtifact("synset.txt").openStream()) {
                    synset = Utils.readLines(is);
                    log.info("Synset loaded successfully with {} classes", synset.size());
                }
            } else {
                // synset 파일이 없는 경우 기본 클래스 사용
                synset = new ArrayList<>(DEFAULT_CLASSES);
                log.info("Using default classes with {} entries", synset.size());
            }
        } catch (IOException e) {
            log.warn("Failed to load synset file, using default classes", e);
            synset = new ArrayList<>(DEFAULT_CLASSES);
        }
    }
}
