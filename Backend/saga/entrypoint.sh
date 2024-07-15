#!/bin/bash

./mvnw clean package -DskipTests
cp target/saga-*.jar /app.jar
eval "$@"