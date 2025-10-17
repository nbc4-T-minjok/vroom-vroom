# [8조] T의민족 - 주문 배달 플랫폼

<br>
<div align="center">
  <img src="https://github.com/user-attachments/assets/13190b31-c653-4858-b72b-7022defc5b5f" width="400" height="400" ></img>
</div>

<br>

<div align="center">

### 🏃🏻‍♀️ [vroom-vroom Swagger](http://3.36.75.246/swagger-ui/index.html) 💨

</div>

<br>

---

# 🧑🏻‍💻 팀원 구성

<br>
<div align="center">
  <table>
    <tr>
      <th>강태성</th>
      <th>강현호</th>
      <th>김민식</th>
      <th>김주영</th>
      <th>황태경</th>
      <th>한재현</th>
    </tr>
    <tr>
      <td><img src="https://avatars.githubusercontent.com/u/69503955?v=4" width="100" height="100"/></td>
      <td><img src="https://avatars.githubusercontent.com/u/175274876?v=4" width="100" height="100"/></td>
      <td><img src="https://avatars.githubusercontent.com/u/167049108?v=4" width="100" height="100"/></td>
      <td><img src="https://avatars.githubusercontent.com/u/84488362?v=4" width="100" height="100"/></td>
      <td><img src="https://avatars.githubusercontent.com/u/115198651?v=4" width="100" height="100"/></td>
      <td><img src="https://avatars.githubusercontent.com/u/174947425?v=4" width="100" height="100"/></td>
    </tr>
    <tr>
      <td><a href="https://github.com/kangkings">@kangkings</a></td>
      <td><a href="https://github.com/hinoyat">@hinoyat</a></td>
      <td><a href="https://github.com/minsik0">@minsik0</a></td>
      <td><a href="https://github.com/eddie412">@eddie412</a></td>
      <td><a href="https://github.com/HwangTaeGyeong">@HwangTaeGyeong</a></td>
      <td><a href="https://github.com/jjaenie">@jjaenie</a></td>
    </tr>
  </table>
</div>
<br>

---

# 🔧 기술 스택

### Backend
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=springboot&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=springsecurity&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/SpringDataJPA-%233574A3?style=flat&logo=SpringDataJPA&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Oauth-%23003545?style=flat&logo=oauth&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/JWT-000000?style=flat&logo=jsonwebtokens&logoColor=white">&nbsp;

### DB/Storage
<img src="https://img.shields.io/badge/PostgreSQL-%23003545?style=flat&logo=postgreSQL&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat&logo=amazons3&logoColor=white">&nbsp;

### VCS
<img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white">&nbsp;

### Cooperation
<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=notion&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Slack-F46800?style=flat&logo=Slack&logoColor=white">&nbsp;

### CI/CD
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white">&nbsp;

### Infra
<img src="https://img.shields.io/badge/Amazon EC2 -FFB746?style=flat&logo=ec2&logoColor=white">&nbsp;

### Docs
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=swagger&logoColor=white">&nbsp;

---
# 🔮 프로젝트 개요

- **프로젝트명:** T의 민족

<!--
- **프로젝트 기간:** 2025.09.26 ~ 2025.10.20
- **프로젝트 형태:** 스프링 부트 기반의 모놀리식 어플리케이션
-->
- **팀원 역할:**

| 이름 | 역할/직책   | 주요 담당 모듈               | 기타 핵심 업무                 |
| :--- |:--------|:-----------------------|:-------------------------|
| **강태성** | **팀장**  | 회원, 보안 (인증/인가), 업체(조회) | 팀 총괄 지휘                  |
| **강현호** | 부팀장     | 주문, 장바구니, 결제           | QA        , 샘플 데이터 생성    |
| **황태경** | 팀원      | 업체 (Company)           | QA          , 문서         |
| **김주영** | 팀원      | 리뷰 (Review)            | 메인 QA                    |
| **한재현** | 팀원      | 배송지                    | 인프라 (AWS EC2, S3 연동), 배포 |
| **김민식** | 팀원      | 메뉴 (Menu)              | AI (OpenAI 연동 등)         |

- **목표:**<br>
  1. 지역 반경 내 업체를 효율적으로 탐색하고 주문까지 연결
  2. 사용자, 업체, 관리자 간의 통합 주문 관리 시스템 구축

```
본 프로젝트는 단순 CRUD를 넘어서, 실제 서비스의 흐름(회원 → 주문 → 결제)을 반영하여 설계하였습니다.  
로그(Audit) 기록, JWT 인증, JPA 최적화, 예외 처리, Swagger 기반 API 문서화 등을 사용합니다.  
사용자(CUSTOMER)는 음식점을 검색해 메뉴를 장바구니에 담아 주문할 수 있으며, 결제 후 배달 과정을 확인할 수 있습니다.  
사장님(OWNER)은 메뉴를 등록/관리하고 주문 현황을 조회할 수 있으며,  
관리자(MANAGER/MASTER)는 모든 권한을 가지며 전체 회원 및 주문 내역을 관리할 수 있습니다.
```

### [👉 기능 상세보기](https://www.notion.so/teamsparta/27a2dc3ef5148012912bc3d3cbcded43)

---

# 📜 프로젝트 설계





<details> 
  <summary><h2> 📃 API 명세서 </h2></summary>

### [API 명세서 바로가기](https://www.notion.so/teamsparta/8-T-27a2dc3ef514803e9588e7af8d93c337)
</details>

<details> 
  <summary><h2> 📝 ERD </h2></summary>
  <img src="https://github.com/user-attachments/assets/a84bd55d-997c-441d-8fc1-f9a203700bbf"></img>
</details>

<details> 
  <summary><h2> ⚙️ 시스템 아키텍처 </h2></summary>
  <img src="https://github.com/user-attachments/assets/9581162e-69c3-4871-8b53-4bd1008d3560"></img>
</details>

<details> 
  <summary><h2> 📑 폴더 구조  </h2></summary>

```
vroomvroom/
├── domain/                              # 주요 도메인 계층
│   ├── address/                         # 주소 관리
│   ├── ai/                              # AI API 로그 및 서비스 (Gemini 연동)
│   ├── cart/                            # 장바구니
│   ├── company/                         # 업체 및 영업시간
│   ├── menu/                            # 메뉴 관리
│   ├── order/                           # 주문
│   ├── payments/                        # 결제
│   ├── region/                          # 행정구역 (시도/시군구/읍면동)
│   ├── review/                          # 리뷰 및 사장님 답글
│   └── user/                            # 회원 및 인증 관련
│
│   └── 각 도메인 구성:
│        ├── controller/   → 요청 수신 및 API 처리
│        ├── service/      → 비즈니스 로직
│        ├── repository/   → JPA 인터페이스
│        └── model/        → entity, dto(request/response)
│
├── global/                              # 전역 설정 및 공통 모듈
│   ├── config/                          # Security, Querydsl, WebClient 설정
│   ├── conmon/                          # 공통 유틸 (S3, Swagger, 상수 등)
│   ├── exception/                       # 예외 처리 핸들러
│   ├── security/                        # JWT, 인증/인가 필터 및 핸들러
│   └── utils/                           # JWT 유틸, 응답 헬퍼
│
└── VroomVroomApplication.java           # 애플리케이션 진입점

```
</details>

---

# 📚  서비스 실행 방법 (TODO)

### ⚙️ 환경 설정

프로젝트는 `application.yml` 을 기반으로 설정됩니다.  
아래 yml은 **local 환경 실행용 기본 설정**입니다.  
(※ 보안을 위해 실제 계정 정보 및 키는 예시 값으로 작성하였습니다.)

```
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: ${DDL_AUTO:update} 
    show-sql: ${SHOW_SQL:true} 
    properties:
      hibernate:
        format_sql: true
  mail:
    host: smtp.naver.com
    port: 587
    username: ${EMAIL} 
    password: ${EMAIL_APP_PASSWORD}    
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
    default-encoding: UTF-8

server:
  port: ${SERVER_PORT:8080}

aws:
  s3:
    bucket: ${S3_BUCKET_NAME}
    access-key: ${S3_ACCESS_KEY}
    secret-key: ${S3_SECRET_KEY}
    region: ${S3_REGION:ap-northeast-2}

jwt:
  secret: ${JWT_SECRET_KEY}
  access-expiration: ${JWT_ACCESS_EXPIRATION:3600000} 

logging:
  level:
    root: INFO
    com.sparta.vroomvroom: DEBUG

gemini:
  api:
    url: "https://generativelanguage.googleapis.com/v1beta/models/gemini-robotics-er-1.5-preview:generateContent"
    key: ${GEMINI_API_KEY}

```

<br>

## 🗄️ 데이터베이스 준비 & 스키마 적용

1. 프로젝트용 PostgreSQL 설치 및 기본 세팅
```
  1. PostgreSQL 설치 (17버전 사용했음)
  2. 버전에 호환되는 PostGIS 설치
  3. PostgreSQL의 슈퍼유저로 DB 연결 (기본 이름 postgres)
  4. 스프링에서 로그인할 사용자(이름,비밀번호) 생성 → 이 계정이 슈퍼유저 권한을 갖도록 만들거나 기존 슈퍼유저에 비밀번호 설정해서 사용
  5. 데이터베이스 생성 (vroomvroom)
  6. 사용할 데이터베이스에 슈퍼유저로 연결해서 Create EXTENSION postgis; 입력
  7. 설치되는거 확인
```

2. PostgreSQL 실행 & DB 생성 (macOS 환경)

```bash
# PostgreSQL 17버전 설치 (homebrew)
brew install postgresql@17

# PostgreSQL 실행
brew services start postgresql

# 쉘에서 DB 로그인
psql -d postgres 

# 슈퍼유저 역할 만들기
CREATE ROLE postgres WITH LOGIN SUPERUSER PASSWORD 'qwer1234';

# DB 생성
CREATE DATABASE vroomvroom;

# PostGIS 설치
brew install postgis

# 연결하기
# 클라이언트 프로그램 (디비버)에서 커넥션 생성

# 생성한 데이터베이스에 PostGIS 추가(슈퍼유저로 로그인해서 쿼리 실행)
CREATE EXTENSION postgis;

# 좌표계 추가
# 프로젝트 루트 디렉토리에 첨부된 coordi system 내용을 복사해서 쿼리로 실행
```

* 아래 링크에 따라 지리데이터 삽입 후 테이블에 맞게 가공 처리 합니다.<br>
  [👉 지리데이터 삽입](https://teamsparta.notion.site/PostgreSQL-PostGIS-28d2dc3ef51480fbab92e531b06443fc?pvs=74) <br><br>
* 아래 제공된 링크에 테스트 데이터를 sql문으로 삽입합니다.<br>
  [👉 테스트 데이터](https://www.notion.so/teamsparta/28b2dc3ef514806e9c6fd28127b69d16) <br><br>

<br>

## ⚙️  빌드 & 실행

```bash
./gradlew clean build
java -jar build/libs/vroomvroom-0.0.1-SNAPSHOT.jar
```
