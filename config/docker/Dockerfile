FROM openjdk:8-jre-alpine
WORKDIR /
ADD target/limit-order-svc-1.0-SNAPSHOT-fat.jar app.jar
EXPOSE 8080
CMD java -jar app.jar