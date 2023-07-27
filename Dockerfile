FROM openjdk:17-alpine
USER root
WORKDIR /app
COPY build/libs/*.jar .
CMD ["java", "-jar", "/app/cbr-0.1-all.jar"]
#--------------------------------------------
#FROM openjdk:17-alpine
#WORKDIR /home/app
#COPY build/docker/main/layers/libs /home/app/libs
#COPY build/docker/main/layers/resources /home/app/resources
#COPY build/docker/main/layers/application.jar /home/app/application.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]
#---------------------------------------------
#FROM openjdk:17-jdk-alpine3.13
#ARG JAR_FILE=build/libs/cbr-0.1-all.jar
#COPY ${JAR_FILE} cbr-0.1-all.jar
#ENTRYPOINT ["java","-jar","/cbr-0.1-all.jar"]
#--------------------------------------------
#FROM gradle:7.5.1-jdk17 as builder
#WORKDIR /app
#COPY . .
#RUN gradle assemble
#
#FROM openjdk:17-alpine
#EXPOSE 8080
#COPY --from=builder /app/build/libs/cbr-0.1-all.jar /app/cbr-0.1-all.jar
#ENTRYPOINT ["java","-jar","/app/cbr-0.1-all.jar"]
#------------------------------------------
#FROM gradle:7.5.1-jdk17-alpine as builder
#USER root
#WORKDIR /builder
#ADD . /builder
#RUN ./gradlew build
#
#FROM openjdk:17-alpine
#WORKDIR /app
#EXPOSE 8080
#COPY --from=builder /builder/build/libs/cbr-0.1-all.jar .
#CMD ["java", "-jar", "cbr-0.1-all.jar"]
#----------------------------------------------