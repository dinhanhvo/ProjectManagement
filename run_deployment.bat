@echo off
setlocal enabledelayedexpansion


echo =========================================
echo Stopping the Docker container: eip-spring-container
echo =========================================
docker stop eip-spring-container
if errorlevel 1 (
    echo Container not running or already stopped. Proceeding...
)

echo =========================================
echo Removing the Docker container: eip-spring-container
echo =========================================
docker rm eip-spring-container
if errorlevel 1 (
    echo Container not present. Proceeding...
)

echo =========================================
echo Starting Docker container with Docker Compose
echo =========================================
docker-compose -f docker-compose.yml up --build -d eip-spring-container
if errorlevel 1 (
    echo Docker Compose failed. Exiting.
    exit /b 1
)

echo =========================================
echo Tailing logs for eip-spring-container
echo =========================================
docker logs eip-spring-container -f
