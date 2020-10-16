FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD
MAINTAINER Grupo Colorado

WORKDIR /workspace/app
COPY pom.xml .
COPY src src

RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jre
MAINTAINER Grupo Colorado
RUN addgroup --system spring && adduser --system spring &&  adduser spring spring
USER spring:spring

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=MAVEN_BUILD ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=MAVEN_BUILD ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=MAVEN_BUILD ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.beertech.banco.infrastructure.BancoApplication"]