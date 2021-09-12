FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY settings.xml /home/app
RUN mvn -f /home/app/pom.xml -s /home/app/settings.xml clean install

FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/home-0.0.1-SNAPSHOT.jar /usr/local/lib/webdownloader.jar
EXPOSE 8080
ENTRYPOINT ["bash"]