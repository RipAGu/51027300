# SK m&service 코딩 테스트 - 프로젝트 제출

## 개발자 정보
- **이름**: 송영현
- **연락처**: dudgus907@naver.com

---

## 프로젝트 개요
이 프로젝트는 SK m&service 채용 코딩 테스트를 위해 개발된 웹 애플리케이션입니다. 게시판 서비스의 구현을 목표로 하며, 기본적인 CRUD 기능과 파일 업로드 및 미리보기 기능을 포함하고 있습니다.

---

## 구현된 주요 기능
- 게시글 작성, 수정, 삭제, 조회
- 파일 및 이미지 업로드 기능 (미리보기 지원)
- 검색 기능 (제목 및 작성자 기준)
- 페이지네이션 구현
- JWT 인증을 통한 사용자 인증

---

## 사용된 기술 스택

### 프론트엔드
- Vue.js
- Vue Router
- Axios
- TailwindCSS

### 백엔드
- Spring Boot
- JPA (Hibernate)
- H2 Database
- Spring Security (JWT 인증)

---

## 프로젝트 실행 방법

### 백엔드
1. `sk_subject_backend` 디렉토리로 이동
2. 터미널에서 `./gradlew bootRun` 명령 실행

### 프론트엔드
1. `frontend` 디렉토리로 이동
2. 터미널에서 `npm install` 명령 실행
3. `npm run dev` 명령 실행

---

## 테스트 계정 목록

### 테스트 계정 1
- **아이디**: user1
- **비밀번호**: user123

### 테스트 계정 2
- **아이디**: user2
- **비밀번호**: user123

---

## 주요 페이지 설명

### 게시판 페이지
- 게시글 목록을 확인하거나 새 글을 작성할 수 있습니다.

### 로그인 페이지
- 로그인하여 인증된 사용자가 될 수 있습니다.

---
## DB설계
![DB설계](https://i.postimg.cc/ZYFFRTb1/SCR-20241204-lpwr.png)
