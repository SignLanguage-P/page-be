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

1. 회원 관리 시스템

- 학습현황 표시
- 로그인, 회원가입 구현
- 권한 관리
- 데이터베이스 설계 및 회원 테이블 생성

2. 단어

- 카테고리별로 분류
- 카테고리 조회 및 검색

3. 진행률 확인

- 단어 학습할 때마다 진행 상황 생성 및 업데이트
- 특정 사용자의 진행 상황 조회
- 학습 완료한 단어들 조회
- 학습 완료 여부, 마지막 학습 시간, 학습 횟수

4. 퀴즈

- 난이도 (초급, 중급, 고급) 분류
- 카테고리별로 분류
- 카테고리와 난이도 선택해 랜덤 퀴즈 풀기 가능
- 퀴즈는 사지선다형

5. 카테고리

- 카테고리 CRUD

## 프로젝트 구조

```
src/main/java/com/example/p_project/
├── domain/
│   ├── category/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   ├── word/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   ├── quiz/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── dto/
│   │   └── controller/
│   └── user/
│       ├── entity/
│       ├── repository/
│       ├── service/
│       ├── dto/
│       └── controller/
└── global/
    ├── common/
    │   └── security/
    │       ├── config/
    │       ├── service/
    │       └── model/
    └── auth/
        ├── controller/
        ├── dto/
        └── service/
```

## 구조 설명

- `domain/`: 애플리케이션의 핵심 비즈니스 로직을 포함합니다.
  - `category/`, `word/`, `quiz/`, `user/`: 각 도메인별 기능을 구현합니다.
- `global/`: 애플리케이션 전반에 걸쳐 사용되는 공통 기능들을 포함합니다.
  - `common/`: 공통 유틸리티, 설정 등을 포함합니다.
  - `auth/`: 인증 관련 기능을 담당합니다.

이 구조는 도메인 중심 설계를 따르며, 관심사의 분리와 재사용성을 고려하여 설계되었습니다.
