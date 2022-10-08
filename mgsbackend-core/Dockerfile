FROM openjdk:8

ARG PROJ_NAME=backend-core
ARG JAR_FILE=target/*.jar
ARG PORT=16002

ENV TZ=Asia/Shanghai
ENV SPRING_PROFILES_ACTIVE=prod

WORKDIR /home/${PROJ_NAME}
COPY ${JAR_FILE} backend-core.jar
COPY target/lib ./lib

EXPOSE ${PORT}

ENTRYPOINT java -jar backend-core.jar