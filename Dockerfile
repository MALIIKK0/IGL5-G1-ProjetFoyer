FROM openjdk:17-jdk-alpine
ADD target/tpFoyer-17-2.0.0.jar tpFoyer-17-2.0.0.jar 
EXPOSE 8080
ENTRYPOINT ["java","-jar","/tpFoyer-17-2.0.0.jar"]
