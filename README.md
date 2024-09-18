# Book Webstore

## Introduction

Welcome to the **Book Webstore** project! This project was inspired by the need to create an easy-to-use and efficient platform for buying books online. The **Book Webstore** allows users to browse through a wide selection of books, manage their shopping cart, and securely place orders. The project is designed with a modern tech stack to ensure a smooth and secure shopping experience.

## Technologies and Tools Used

This project utilizes a range of technologies and frameworks, including:

- **Java 17** 
- **Spring Boot** – for the core application framework
- **Spring Security** – for authentication and authorization.
- **Spring Data JPA** – for data access and interaction with a MySQL database.
- **Spring Validation** – for validating user inputs with both built-in and custom annotations.
- **JWT (JSON Web Tokens)** – for secure user authentication.
- **Swagger** – for API documentation.
- **Testcontainers** – for running integration tests with isolated MySQL instances in Docker.
- **Docker** – for containerized deployments.
- **MapStruct** – for mapping between Java bean types.
- **Global Exception Handling** – for centralized error handling to manage exceptions across the application.

These technologies work together to deliver a feature-rich, secure, and maintainable web application.

## Project Functionalities

This application provides a comprehensive set of features for managing an online book store:

- **User Authentication**: Secure registration and login processes are facilitated through the `AuthenticationController`, allowing users to create accounts and access their profiles with JWT-based authentication.

- **Book Management**: The `BookController` offers extensive functionality for interacting with the book catalog. Users can browse through all available books, search for books by title or author, and view detailed book information. Administrators can also add, update, or delete book entries to keep the catalog current and relevant.

- **Category Organization**: The `CategoryController` helps organize the book catalog into manageable categories. Users can view all categories, retrieve information about specific categories, and explore books within each category. This functionality enhances the browsing experience and helps users find books based on their interests. Administrators have the capability to create new categories, update existing ones, and remove categories as needed.

- **Order Processing**: Through the `OrderController`, users can place new orders for books, while administrators can manage and track all orders. This includes updating order statuses and accessing detailed information about individual order items. This functionality ensures a streamlined ordering process and effective order management.

- **Shopping Cart Management**: The `ShoppingCartController` provides essential features for managing the shopping cart. Users can view their cart, add or remove books, adjust quantities, and clear the cart. This functionality allows users to easily manage their selections before finalizing their purchases.

These features are designed to create a user-friendly and efficient online shopping experience, addressing the needs of both customers and administrators. For detailed API interactions, refer to the Swagger documentation and test the endpoints using Postman.


## Project Setup

To simplify the process of running the application, Docker is configured. You can run the entire application, including the MySQL database, using Docker. Follow these steps:

1. Ensure that **Docker** is installed and running on your machine.
2. Clone the project from GitHub:
   ```
   git clone https://github.com/BohdanZorii/book-web-store.git
   ```
3. Navigate to the project directory:
   ```
   cd book-webstore
   ```
4. Build and start the containers using Docker Compose:
   ```
   docker-compose up --build
   ```
   This command will build the Docker images and start the containers for both the Spring Boot application and MySQL database.


5. The application should now be running!

## API Documentation
To explore and test the API endpoints, you can use Swagger. Swagger provides interactive API documentation that allows you to test endpoints directly from the browser.

- **[Swagger Documentation](http://localhost:8080/api/swagger-ui/index.html)**

## Postman Collection

To test the API endpoints, you can use the Postman collection provided below. Make sure your application is running through Docker before importing the collection.

- **[Postman Collection](https://www.postman.com/science-technologist-21014905/bohdan-zorii/collection/5nvux3s/book-webstore?action=share&creator=28808144)**

## Challenges & Solutions

During the development of the Book Webstore, several challenges emerged, each requiring tailored solutions to ensure the project’s success.

One of the key hurdles was implementing secure authentication. To address this, JWT-based authentication was integrated with Spring Security, which allowed for secure token management and proper role-based access control for both users and admins.

Another challenge was ensuring reliable testing environments. To simulate a production-like database setup, Testcontainers was employed to create isolated MySQL instances. This approach ensured that tests ran consistently, closely mirroring real-world conditions.

Filtering and searching within large datasets proved to be complex. The JPA Criteria API was leveraged to build dynamic queries, which allowed for efficient and accurate data retrieval despite the volume of information.

Handling custom validation requirements, such as ensuring valid order statuses and matching passwords during user registration, required creating specific validation annotations. These custom rules were integrated into the validation framework to enforce business logic effectively.

These challenges were met with targeted solutions, leading to a more robust and user-friendly Book Webstore application.


