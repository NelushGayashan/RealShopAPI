# RealShop API

RealShop API is a fully-fledged RESTful service for managing an e-commerce platform. This application allows for the management of products, cart items, and provides functionalities such as filtering, pagination, and searching. Built with **Spring Boot**, **Spring Data JPA**, and **H2 Database** (or any relational database), it is designed to serve as a backend for a dynamic e-commerce web or mobile application.

## Table of Contents
1. [Features](#features)
   - [Product Management](#product-management)
   - [Cart Management](#cart-management)
   - [Error Handling](#error-handling)
2. [Tech Stack](#tech-stack)
3. [Requirements](#requirements)
4. [Setup](#setup)
   - [Clone the Repository](#clone-the-repository)
   - [Configure Database](#configure-database)
   - [Build & Run the Application](#build--run-the-application)
5. [API Endpoints](#api-endpoints)
   - [Product Endpoints](#product-endpoints)
   - [Cart Endpoints](#cart-endpoints)
6. [Request Examples](#request-examples)
7. [Advanced Setup](#advanced-setup)
   - [Integrating JWT Authentication (Optional)](#integrating-jwt-authentication-optional)
   - [Using Swagger for API Documentation](#using-swagger-for-api-documentation)
8. [Testing](#testing)
9. [Contribution Guidelines](#contribution-guidelines)
10. [License](#license)

## Features

### Product Management
- **CRUD Operations**: Add, update, and delete products.
- **Product Search**: Search products by title (case-insensitive), price, and category.
- **Filter Products**: Fetch products by price, category, and more.
- **Distinct Categories**: Retrieve distinct categories available in the store.

### Cart Management
- **CRUD Operations**: Create, update, and delete user carts.
- **Item Management**: Manage cart items with product IDs and quantities.
- **Date Range Filtering**: Filter carts based on creation date range.
- **Pagination & Sorting**: Fetch paginated results with sorting options for better user experience.

### Error Handling
- **Custom Exceptions**: Handle errors with descriptive messages for invalid operations (e.g., product not found, cart not found).
- **Response Handling**: All responses are structured in a uniform way, with detailed error messages.

## Tech Stack

- **Backend Framework**: Spring Boot 3.4.0
- **Database**: H2 (default for development), MySQL/PostgreSQL (for production environments)
- **ORM**: Spring Data JPA
- **Authentication**: Optional (JWT authentication can be added)
- **Documentation**: OpenAPI/Swagger for API documentation (optional, can be added)

## Requirements

- **Java 23**
- **Maven** (or Gradle)
- **IDE**: IntelliJ IDEA, VS Code, Eclipse, or any preferred IDE

## Setup

### Clone the Repository

```bash
git clone https://github.com/NelushGayashan/RealShopAPI.git
cd RealShopAPI
```

### Configure Database
By default, the project uses H2 Database for simplicity. If you're setting up for production, configure MySQL, PostgreSQL, or any other relational database in the application.properties file.

#### Example (MySQL):
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/realshop
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
For H2 (default), no configuration is needed; the app runs in memory.

## Build & Run the Application

1. **Build the project**:
Using Maven:
```bash
mvn clean install
```

2. **Run the application**:
```bash
mvn spring-boot:run
```
The API will be available at http://localhost:8080.

## API Endpoints

### Product Endpoints

#### **GET /products**  
Retrieves all products with optional pagination.  
**Query Parameters:**  
- `page` (optional): Page number  
- `size` (optional): Number of items per page  
- `sort` (optional): Sorting criteria  

---

#### **GET /products/{id}**  
Fetches a specific product by its ID.

---

#### **GET /products/category/{category}**  
Fetches products by category.

---

#### **GET /products/search?title={title}**  
Search products by title (case-insensitive).

---

#### **POST /products**  
Creates a new product.  
**Request Body:**  
```json
{
  "title": "Product Name",
  "price": 29.99,
  "description": "Description of the product",
  "image": "image_url",
  "category": "Electronics"
}
```
---

#### **PUT /products/{id}** 
Updates an existing product by ID.
Request Body: Similar to the `POST /products` endpoint.

---

DELETE /products/{id}
Deletes a product by its ID.

---

## Cart Endpoints

### `GET /carts`
Fetch all carts with optional filters.

#### Query Parameters:
- **`userId` (optional):** Filter by user ID  
- **`startdate` (optional):** Start date for creation date range  
- **`enddate` (optional):** End date for creation date range  
- **`limit` (optional):** Number of items to fetch  
- **`sort` (optional):** Sorting criteria  

---

GET /carts/{id}
Fetches a specific cart by its ID.

---

POST /carts
Creates a new cart with associated cart items.
**Request Body:** 
```json
{
  "userId": 123,
  "cartItems": [
    { "productId": 1, "quantity": 2 },
    { "productId": 2, "quantity": 1 }
  ]
}
```

---

PUT /carts/{id}
Updates an existing cart with new cart items.
**Request Body:** Similar to the `POST /carts` endpoint.

---

DELETE /carts/{id}
Deletes a cart by its ID.

---

## Request Examples

### Creating a Product
**Endpoint:** `POST /products`  

**Request Body:**
```json
{
  "title": "Smartphone X100",
  "price": 499.99,
  "description": "The latest smartphone with advanced features.",
  "image": "http://example.com/images/smartphone.jpg",
  "category": "Electronics"
}
```

### Creating a Cart with Items
**Endpoint:** `POST /carts`  

**Request Body:**
```json
{
  "userId": 12345,
  "cartItems": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 5,
      "quantity": 1
    }
  ]
}

```

## Advanced Setup

### Integrating JWT Authentication (Optional)
1. **Add Dependencies**:  
   Add Spring Security and JWT dependencies.  
   Example of required dependencies:  
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <dependency>
       <groupId>io.jsonwebtoken</groupId>
       <artifactId>jjwt</artifactId>
       <version>0.11.2</version>
   </dependency>
   ```

   ### Implement Authentication

- **JWT-Based Authentication Filter**:  
  Implement a filter that validates and processes JWT tokens for secure API access.

- **User Management Endpoints**:  
  Create the following endpoints:  
  - **Login**: For user authentication and token generation.  
  - **Registration**: For adding new users to the system.  


### Using Swagger for API Documentation

#### Add Dependency:
Add the following dependency to your project:

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```
### Configure Swagger:
Configure Swagger in your Spring Boot application:

```java
@Configuration
@EnableOpenApi
public class SwaggerConfig {                                    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("RealShop API").version("1.0")
                .description("RealShop API documentation"));
    }
}
```

### Access Documentation:
After running the application, navigate to http://localhost:8080/swagger-ui/ for interactive API documentation.


## Testing

### Run Unit Tests:
Ensure you have JUnit and Mockito dependencies in your project.
Run the tests using:

```bash
mvn test
```

## Contribution Guidelines

### Fork the Repository:
Create a personal copy of the repository.

### Create a Feature Branch:
Work on your changes in a new branch, e.g., `feature/add-pagination`.

### Write Tests:
Ensure new features are covered with appropriate unit tests.

### Submit a Pull Request:
Once your changes are complete, submit a pull request for review.

## License
This project is licensed under the MIT License - see the LICENSE file for details.




   
