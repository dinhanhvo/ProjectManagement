#!/bin/sh
echo "Waiting for Redis..."
while ! nc -z redis 6379; do
    sleep 1
done
echo "Redis is up - starting the application"
exec java -jar app.jar
