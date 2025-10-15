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

| 이름 | 역할/직책 | 주요 담당 모듈 | 기타 핵심 업무 |
| :--- | :--- | :--- | :--- |
| **강태성** | **팀장** | 회원, 보안 (인증/인가) | 팀 총괄 지휘 |
| **강현호** | TL (기술 리더) | 주문, 장바구니, 결제 | - |
| **황태경** | 팀원 | 업체 (Company) | - |
| **김주영** | 팀원 | 리뷰 (Review) | - |
| **한재현** | 팀원 | 배송지 | 인프라 (AWS EC2, S3 연동), 배포 |
| **김민식** | 팀원 | 메뉴 (Menu) | AI (OpenAI 연동 등) |

- **목표:** Spring Boot와 JPA를 활용해 개발한 음식 주문 배달 플랫폼입니다.

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
<!-- ### 📃 [API 명세서 바로가기](https://www.notion.so/teamsparta/8-T-27a2dc3ef514803e9588e7af8d93c337) -->

<details> 
  <summary><h2> 📝 ERD </h2></summary>
  <img src="https://github.com/user-attachments/assets/a84bd55d-997c-441d-8fc1-f9a203700bbf"></img>
</details>

<details> 
  <summary><h2> ⚙️ 시스템 아키텍처 </h2></summary>
  <img src="https://github.com/user-attachments/assets/98c36166-6c89-4591-b1fe-4098d35947d4"></img>
</details>

<details> 
  <summary><h2> 📑 폴더 구조  </h2></summary>

### [👉 폴더 구조 바로가기](/)
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

---


## 🗄️ 데이터베이스 준비 & 스키마 적용

1. PostgreSQL 실행 & DB 생성 (macOS 환경)

```bash
# PostgreSQL 실행
brew services start postgresql

# DB 접속
psql -U YourUsername

-- DB 생성 (처음 한 번만 실행)
CREATE DATABASE vroomvroom;
\q
````

2. 스키마 파일 배치 및 적용

* 아래 제공된 DDL 전체를 파일로 저장합니다.
    * 경로: `src/main/resources/db/hanip.sql`
    * 제공된 스키마를 그대로 사용하면 됩니다.


```bash
스키마 적용 명령어
psql -U YourUsername -d hanip -f src/main/resources/db/schema.sql
```

3. 빌드 & 실행

```bash
./gradlew clean build
java -jar build/libs/justonebite-0.0.1-SNAPSHOT.jar
```

---
