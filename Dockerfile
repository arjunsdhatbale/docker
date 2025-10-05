# Use an official JDK 21 runtime as base image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Install netcat for MySQL wait script
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

# Copy the jar file
COPY target/*.jar app.jar

# Create a wait script for MySQL
RUN echo '#!/bin/bash\n\
echo "Waiting for MySQL to be ready..."\n\
while ! nc -z mysql 3306; do\n\
  echo "MySQL is unavailable - sleeping"\n\
  sleep 2\n\
done\n\
echo "MySQL is up - starting application"\n\
exec java -jar /app/app.jar' > /app/start.sh && chmod +x /app/start.sh

EXPOSE 8080

ENTRYPOINT ["/app/start.sh"]