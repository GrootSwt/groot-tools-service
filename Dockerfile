FROM openjdk:17
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/backend/groot-tools-service.jar"]