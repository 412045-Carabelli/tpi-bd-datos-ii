FROM openjdk:17-jdk-slim

VOLUME /tmp

COPY target/instagram-neo4j-java app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
