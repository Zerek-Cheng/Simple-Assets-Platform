FROM maven:3.8.6-eclipse-temurin-17 AS build
ADD . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:18.0.2.1-jdk-oraclelinux8 AS runtime
COPY --from=build /app/target/*.jar /app/
COPY --from=build /app/target/config/ /app/config
COPY --from=build /app/target/lib/ /app/lib

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "SimpleImgPlatform.jar"]