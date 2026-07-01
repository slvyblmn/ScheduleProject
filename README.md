# Schedule API Project

Spring Boot를 이용한 일정 관리 REST API 프로젝트입니다.

## 개발 목표

이 프로젝트는 단순 CRUD가 아니라
다음 구조를 이해하고 구현하는 것을 목표로 합니다.

## 3-Layer Architecture 이해

- DTO 분리 설계
- JPA 사용
- Auditing 적용
- REST API 설계

## 기술 스택

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
  
## 3-Layer Architecture

- Controller
- 요청을 받는 역할
- Service 호출 후 응답 반환
- Service
- 비즈니스 로직 처리
- 비밀번호 검증, 데이터 가공
- Repository
- DB 접근 담당
- JPA 기반 데이터 처리

### ERD

Schedule
```
+------------------------------------------------+
|                  Schedule                      |
+------------------------------------------------+
| PK id : BIGINT                                 |
| title : VARCHAR(100)                           |
| info : TEXT                                    |
| writer : VARCHAR(50)                           |
| password : VARCHAR(100)                        |
| created_at : DATETIME                          |
| modified_at : DATETIME                         |
+------------------------------------------------+
```
## API 명세서

### 1. 일정 생성

POST /schedules

Request
```
{
  "title": "전공 국제관계",
  "info": "기말 시험",
  "writer": "왕준호",
  "password": "0626"
}
```
Response
```
{
  "id": 1,
  "title": "전공 국제관계",
  "info": "기말 시험",
  "writer": "왕준호",
  "createdAt": "2026-07-01T10:00:00",
  "modifiedAt": "2026-07-01T10:00:00"
}
```
### 2. 전체 일정 조회

GET /schedules

GET /schedules?writer=왕준호

Response
```
[
  {
    "id": 1,
    "title": "전공 국제관계",
    "info": "기말 시험",
    "writer": "왕준호",
    "createdAt": "2026-07-01T10:00:00",
    "modifiedAt": "2026-07-01T10:00:00"
  }
]
```
### 3. 단건 조회

GET /schedules/{id}

Response
```
{
  "id": 1,
  "title": "전공 국제관계",
  "info": "기말 시험",
  "writer": "왕준호",
  "createdAt": "2026-07-01T10:00:00",
  "modifiedAt": "2026-07-01T10:00:00"
}
```
### 4. 일정 수정

PUT /schedules/{id}

Request
```
{
  "title": "수정 제목",
  "info": "기말 시험",
  "writer": "왕준호",
  "password": "0626"
}
```
Response
```
{
  "id": 1,
  "title": "수정 제목",
  "info": "기말 시험",
  "writer": "왕준호",
  "createdAt": "2026-07-01T10:00:00",
  "modifiedAt": "2026-07-01T10:05:00"
}
```
### 5. 일정 삭제

DELETE /schedules/{id}?password=0626

Response
```
삭제 완료
```
## JPA Auditing

BaseTimeEntity를 통해 자동으로 관리됨:

createdAt : 생성 시간
modifiedAt : 수정 시간

## DTO 사용 이유

Entity 직접 반환 방지
password 노출 방지
응답 구조 분리

## 주요 기능 정리

- 일정 생성
- 전체 조회
- 작성자 조회
- 단건 조회
- 수정 (비밀번호 검증)
- 삭제 (비밀번호 검증)
- JPA Auditing 적용
- DTO 분리

## 과제 질문 답변

### 1. 3-Layer Architecture

Controller는 요청 처리
Service는 로직 처리
Repository는 DB 처리

### 2. DTO 사용 이유

Entity를 그대로 쓰면 password 같은 정보가 노출되기 때문에
필요한 데이터만 전달하기 위해 DTO를 사용한다.

### 3. JPA Auditing

createdAt, modifiedAt을 자동으로 관리해주는 기능이다.

### 4. 비밀번호 검증

수정/삭제 시 무단 접근을 막기 위해 비밀번호를 확인한다.
