FROM openjdk:22-ea-20-jdk-oracle
LABEL authors="Master"

EXPOSE 8081

ADD target/ProfileController-0.0.1-SNAPSHOT.jar myapp.jar

ENTRYPOINT ["java","-jar","/myapp.jar"]