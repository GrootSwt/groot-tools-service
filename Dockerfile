FROM openjdk:17
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/backend/chat-service.jar"]