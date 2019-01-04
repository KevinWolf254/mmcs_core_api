FROM openjdk:8-alpine

ADD target/marketme-api.jar marketme-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "marketme-api.jar"]