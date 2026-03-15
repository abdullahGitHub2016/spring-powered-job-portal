# Step 1: Use a Java Runtime image
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the jar file from your target folder to the container
# Note: You must run 'mvn clean package' first to generate this jar
COPY target/*.jar app.jar

# Step 4: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]