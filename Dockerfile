# 使用官方的 OpenJDK 作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将项目的 JAR 文件复制到容器中
COPY target/groot-tools-service.jar app.jar

# 暴露应用运行的端口
EXPOSE 40021

# 运行 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]