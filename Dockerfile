FROM openjdk:11

WORKDIR /DevOps_Project

COPY target/DevOps_Project.jar DevOps_Project.jar

CMD ["java", "-jar", "DevOps_Project.jar"]
