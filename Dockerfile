# ===== Build stage =====
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# gradle wrapper & 설정만 먼저 복사(의존성 캐시 극대화)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./


RUN chmod +x gradlew
# 의존성만 먼저 당겨와 캐시 확보 (실패해도 캐시 남기려고 || true)
RUN ./gradlew --no-daemon dependencies || true

# 실제 소스 복사 후 빌드
COPY src src
RUN ./gradlew --no-daemon clean bootJar -x test

# ===== Run stage =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 빌드 결과 복사 (경로는 프로젝트에 맞게: 보통 build/libs/*.jar)
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
