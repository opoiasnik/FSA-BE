FROM maven:3.9.11-eclipse-temurin-25 AS builder
LABEL maintainer="Oleh Poiasnik" \
      version="1.0" \
      description="Property Rental Spring Boot backend"

WORKDIR /application

COPY pom.xml ./

COPY application/api-spec                  application/api-spec/
COPY application/domain                    application/domain/
COPY application/inbound-controller-rest   application/inbound-controller-rest/
COPY application/outbound-repository-jpa   application/outbound-repository-jpa/
COPY application/outbound-geocoding-rest   application/outbound-geocoding-rest/
COPY application/springboot                application/springboot/

RUN mvn clean package -pl application/springboot -am -DskipTests

RUN mkdir build \
 && cd build \
 && java -Djarmode=layertools \
      -jar ../application/springboot/target/*.jar extract

FROM eclipse-temurin:25-jre-alpine
WORKDIR /application

COPY --from=builder /application/build/dependencies/           ./
COPY --from=builder /application/build/spring-boot-loader/     ./
COPY --from=builder /application/build/snapshot-dependencies/  ./
COPY --from=builder /application/build/application/            ./

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE 8080

ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} org.springframework.boot.loader.launch.JarLauncher"]
