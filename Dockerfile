FROM openjdk:8-jdk-alpine
ENV PATH=/usr/local/openjdk-8/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin JAVA_HOME=/usr/local/openjdk-8 LANG=C.UTF-8 JAVA_VERSION=8u282
WORKDIR application
COPY target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract
FROM 076608705810.dkr.ecr.us-east-1.amazonaws.com/java:8-jdk-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["sh","-c","java  org.springframework.boot.loader.JarLauncher"]