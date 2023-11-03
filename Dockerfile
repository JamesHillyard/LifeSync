# From an Alpine Linux base with Zulu JDK 17
FROM azul/zulu-openjdk-alpine:17.0.8.1 AS Builder

# Set the working directory
WORKDIR /lifesync

# Install Maven
RUN apk add maven

# Copy the Maven project files to the container
COPY pom.xml .
COPY src ./src

# Compile the application
RUN mvn -f /lifesync/pom.xml clean install -DskipTests

CMD ["java", "-jar", "/lifesync/target/LifeSync.war","--spring.profiles.active=docker"]