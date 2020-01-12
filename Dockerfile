FROM openjdk:11-slim
ADD target/genuniv-general-info-service.jar /jar/genuniv-general-info-service.jar
EXPOSE 19100
ENTRYPOINT ["java", "-jar", "/jar/genuniv-general-info-service.jar"]
