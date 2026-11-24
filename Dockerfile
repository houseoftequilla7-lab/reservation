FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x reservation/mvnw && cd reservation && ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/reservation/target/*.jar app.jar
EXPOSE 10000
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar app.jar"]