FROM openjdk:11-slim
ADD target/genuniv-general-info-service.jar genuniv-general-info-service.jar
EXPOSE 19100
ENTRYPOINT ["java", "-jar", "genuniv-general-info-service.jar"]
