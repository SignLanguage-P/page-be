# 수화 학습용 웹 제작 프로젝트

spring boot를 사용한 수화 학습용 웹 제작 프로젝트

## 제작 기한

2024-10 ~ 2024-12

## 개발 환경

- Gradle Groovy 8.8
- Java 23, Jar
- Spring boot 3.3.4
- Packaging Jar
- MySQL 8.3.0

## 사용 기술 스택

<div align=center>
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"/>
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"/>
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"/>
</div>
<div align=center>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
</div>

## 요구사항


1. 단어

- 카테고리별로 분류
- 카테고리 조회 및 검색

2. 퀴즈

- 난이도 (초급, 중급, 고급) 분류
- 카테고리별로 분류
- 카테고리와 난이도 선택해 랜덤 퀴즈 풀기 가능
- 퀴즈는 컴퓨터 비전 기술 사용해 손짓 인식 후 문제 풀기 가능

3. 카테고리

- 카테고리 CRUD

## 프로젝트 구조

```
src/main/java/com/example/p_project/
├── domain/                          # 도메인 계층
│   ├── Category/                    # 카테고리 도메인
│   │   ├── controller/             # REST API 컨트롤러
│   │   ├── dto/                    # 요청/응답 데이터 객체
│   │   ├── entity/                 # JPA 엔티티
│   │   ├── repository/             # JPA 레포지토리
│   │   └── service/                # 비즈니스 로직
│   │
│   ├── Quiz/                        # 퀴즈 도메인
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   │
│   ├── SignLanguage/                # ai 모델 도메인
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   │
│   └── Word/                        # 단어 도메인
│       ├── controller/
│       ├── dto/
│       ├── entity/
│       ├── repository/
│       └── service/
│
├── config/                          # 전역 설정
│   └── WebClientConfig             # WebClient 설정
│                                   # FastAPI 통신
│
└── global.config/                   # 글로벌 설정
    ├── JpaConfig                    # JPA 설정
    └── SecurityConfig              # 보안 설정

```

## 📌 Package 설명

### **🔹 domain**

애플리케이션의 핵심 비즈니스 로직을 포함하는 패키지입니다. 각 도메인 패키지는 다음과 같은 구조를 가집니다:

- controller/: REST API 엔드포인트
- dto/: 데이터 전송 객체
- entity/: JPA 엔티티
- repository/: 데이터 접근 계층
- service/: 비즈니스 로직

### **🔹 config**
- WebClientConfig: FastAPI 서버와의 통신을 담당하는 설정
- 수어 인식 AI 모델 서버와의 연동
- 비동기 HTTP 통신 처리

### **🔹 global**
- JpaConfig: 데이터베이스 연결 설정
- SecurityConfig: 보안 및 인증 설정

## 🎯 설계 원칙
- 도메인 주도 설계(DDD) 적용
- 관심사 분리를 통한 유지보수성 향상
- 도메인별 독립성 보장
- 공통 기능의 재사용성 확보
- MSA 고려 (FastAPI 서버 연동)

이 구조는 도메인 중심 설계를 따르며, 관심사의 분리와 재사용성을 고려하여 설계되었습니다.
