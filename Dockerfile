# ---------- Build ----------
FROM maven:3.8.1-openjdk-8 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# ---------- Runtime ----------
FROM openjdk:8-jdk-alpine

# 1) Instalar fontconfig + fuentes DejaVu (para PDFs)
RUN apk add --no-cache fontconfig ttf-dejavu

# 2) Forzar headless y UTF-8 a nivel JVM
ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=true -Dfile.encoding=UTF-8"

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 3) Ejecutar
ENTRYPOINT ["java", "-Xms64m", "-Xmx128m", "-XX:+UseG1GC", "-jar", "app.jar"]
