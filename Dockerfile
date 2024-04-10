FROM openjdk:17-jdk-alpine
COPY build/libs/shop-0.0.1-SNAPSHOT.jar /shop-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/shop-0.0.1-SNAPSHOT.jar"]

