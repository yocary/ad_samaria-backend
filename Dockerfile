# Usa una imagen base de Maven
FROM maven:3.8.1-openjdk-8 as build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y el código fuente
COPY pom.xml .
COPY src ./src

# Compila la aplicación
RUN mvn package -DskipTests

# Usa una imagen base de Java para la ejecución
FROM openjdk:8-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el jar generado al contenedor
COPY --from=build /app/target/*.jar app.jar

# Establece el comando para ejecutar la aplicación con opciones de memoria
ENTRYPOINT ["java", "-Xms64m", "-Xmx128m", "-XX:+UseG1GC", "-jar", "app.jar"]