FROM maven:3.8.3-openjdk-17 AS springboot_build
COPY . /home/app
WORKDIR /home/app
RUN mvn -f /home/app/pom.xml clean install
RUN dir -s
# List directories

FROM openjdk:17
LABEL authors="Ashish"
COPY --from=springboot_build /home/app/target/asdc-dalbites-backend.jar /app/asdc-dalbites-backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/asdc-dalbites-backend.jar"]
