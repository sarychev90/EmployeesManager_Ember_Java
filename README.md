The "Employees Manager" project offers the user a simple functionality for managing the filling of the company with the structure of departments and employees with basic necessary information. In the project, the base CRUD functionality is available using REST services.

A Swagger-based OpenAPI interface is available in the project. After deploying the project, the user can get acquainted in detail with the structure of the REST services methods at the link: http://localhost:8080/swagger-ui.html#/employees-manager-data-controller

This project implements the concept of a client-server application with support for a micro-service architecture based on the Docker infrastructure.

There are just few simple steps to build and run the application locally:
1. Build Java Spring Boot application using Maven.
2. Start the deployment of the project using the command "docker-compose up" from the root directory of the project.
3. Go to http://localhost:9999 where the program interface will unfold for the user.

The project consists of three parts: Front-End UI based on Ember Octane JS framework, Back-End application based on Java Spring Boot framework, and also using a database (in this case, PostgreSQL was used).
