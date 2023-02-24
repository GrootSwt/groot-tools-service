FROM openjdk:17
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "chat-service.jar"]