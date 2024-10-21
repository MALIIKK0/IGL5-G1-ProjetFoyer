FROM openjdk:17-jdk-alpine
ADD target/tpFoyer-17-0.0.1-SNAPSHOT.jar tpFoyer-17-0.0.1-SNAPSHOT.jar 
EXPOSE 8080
ENTRYPOINT ["java","-jar","/tpFoyer-17-0.0.1-SNAPSHOT.jar"]