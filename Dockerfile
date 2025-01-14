# 1. 기본 이미지는 Gradle을 포함한 OpenJDK 21 이미지
FROM gradle:8.0.2-jdk17 AS builder

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 프로젝트 파일 복사
COPY . .

# 4. Gradle 빌드 실행 (build/libs/*.jar로 결과물이 생성)
RUN gradle bootJar --no-daemon --info

# 5. 실제 애플리케이션을 실행할 OpenJDK 21 기반 이미지
FROM bellsoft/liberica-openjdk-alpine:21

COPY --from=builder /app/build/libs/*.jar app.jar
## 2. ARG를 사용해 빌드 결과 JAR 파일의 경로를 지정
#ARG JAR_FILE=build/libs/*.jar
#
## 3. 위에서 정의한 JAR 파일을 컨테이너의 app.jar로 복사
#COPY ${JAR_FILE} app.jar

# 4. 컨테이너 시작 시 JAR 파일을 실행하는 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
