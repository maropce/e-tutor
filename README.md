# Etutor

## Introduction
The `Etutor` project runs using Docker Compose, which allows for quick and easy startup of all required services and dependencies. Below, you’ll find instructions for building and running the application.

## Requirements
- **Docker**: The application requires Docker to manage containers.
- **Docker Compose**: Ensure that Docker Compose is installed.

## Starting the Application

1. **Building Containers**  
   Before the first run or after making changes to the code, build the containers using the following command:

   ```bash
   docker compose build
   ```

2. **Running the Application**  
   After building the containers, start the application:

   ```bash
   docker compose up
   ```

   The application will start, and all containers will launch in the appropriate order.

3. **Verifying the Application**  
   - Make sure the application is running correctly by checking Docker logs.
   - If you need to stop the application, you can either use `Ctrl + C` in the terminal or run the following command to stop all containers:

     ```bash
     docker compose down
     ```
