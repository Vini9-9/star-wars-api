FROM maven:3.6.0-jdk-11-slim AS build  
COPY . .
RUN mvn clean package -f pom.xml

FROM adoptopenjdk/openjdk11:alpine 
COPY --from=build target/*.jar app.jar  
ENTRYPOINT ["java","-jar","app.jar"]