FROM eclipse-temurin:21-jre-alpine

WORKDIR /deployments

COPY target/quarkus-app/ ./

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]