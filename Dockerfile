# ---------- Build ----------
FROM maven:3.9.9-eclipse-temurin-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# ---------- Runtime ----------
FROM eclipse-temurin:8-jre-jammy

# 1) Instalar fontconfig + DejaVu (PDFs)
RUN apt-get update \
 && apt-get install -y --no-install-recommends fontconfig fonts-dejavu \
 && rm -rf /var/lib/apt/lists/*

# 2) Forzar headless y UTF-8
ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=true -Dfile.encoding=UTF-8"

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 3) Ejecutar
ENTRYPOINT ["java","-Xms64m","-Xmx128m","-XX:+UseG1GC","-jar","app.jar"]
