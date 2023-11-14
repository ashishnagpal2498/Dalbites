FROM maven:3.8.3-openjdk-17 AS springboot_build
COPY . /home/app
WORKDIR /home/app
RUN mvn -f /home/app/pom.xml clean install

FROM openjdk:17
LABEL authors="Ashish"
EXPOSE 8080
ADD target/asdc-dalbites-backend.jar asdc-dalbites-backend.jar
ENTRYPOINT ["java","-jar","/asdc-dalbites-backend.jar"]