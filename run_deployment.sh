#!/bin/bash

# Enable strict mode to handle errors
set -euo pipefail

echo ">>> Pulling latest code from Git repository"
git pull || { echo "Failed to pull from Git. Exiting."; exit 1; }

echo ">>> Cleaning and packaging the Maven project"
mvn clean package -DskipTests || { echo "Maven build failed. Exiting."; exit 1; }

echo ">>> Stopping the Docker container: eip-spring-container"
docker stop eip-spring-container || echo "Container not running, proceeding."

echo ">>> Removing the Docker container: eip-spring-container"
docker rm eip-spring-container || echo "Container not present, proceeding."

echo ">>> Starting Docker container with Docker Compose"
docker-compose -f docker-compose.yml up --build -d eip-spring-container || { echo "Docker Compose failed. Exiting."; exit 1; }

echo ">>> Tailing logs for eip-spring-container"
docker logs eip-spring-container -f
