FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY finatiol-common/pom.xml ./finatiol-common/pom.xml
COPY finatiol-common/src ./finatiol-common/src
RUN mvn -f finatiol-common/pom.xml install -DskipTests -q
COPY finatiol-autenticacion-ms/pom.xml ./finatiol-autenticacion-ms/pom.xml
COPY finatiol-autenticacion-ms/src ./finatiol-autenticacion-ms/src
RUN mvn -f finatiol-autenticacion-ms/pom.xml package -DskipTests -q

FROM eclipse-temurin:21-jre
WORKDIR /app
EXPOSE 8081
COPY --from=build /app/finatiol-autenticacion-ms/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]