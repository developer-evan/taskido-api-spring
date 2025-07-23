
```markdown
# Taskido - Task Management API

A RESTful API for task management built with Spring Boot and MongoDB, featuring JWT authentication and comprehensive API documentation.

## Features

- 🔐 JWT-based authentication and authorization
- 📝 Full CRUD operations for tasks
- 👤 User registration and management
- 📚 Interactive API documentation with Swagger UI
- 🗄️ MongoDB integration
- 🛡️ Security configuration with Spring Security
- ⚡ Built with Spring Boot for rapid development

## Technology Stack

- **Framework**: Spring Boot
- **Database**: MongoDB
- **Security**: Spring Security with JWT
- **Documentation**: OpenAPI 3 (Swagger)
- **Build Tool**: Maven
- **Java Version**: Compatible with modern Java versions


## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- MongoDB instance (local or cloud)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd taskido
   ```

2. **Configure MongoDB**
   
   Update the MongoDB URI in `src/main/resources/application.yml`:
   ```yaml
   spring:
     data:
       mongodb:
         uri: "mongodb://localhost:27017/taskido"
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## Configuration

### Environment Variables

You can override configuration using environment variables or update `application.yml`:

- **MongoDB URI**: Update `spring.data.mongodb.uri`
- **JWT Secret**: Update `jwt.secret` (use a secure secret in production)
- **JWT Expiration**: Update `jwt.expiration` (in milliseconds)
- **Server Port**: Update `server.port`

### Security Configuration

The application uses JWT tokens for authentication with a 24-hour expiration time by default.

## API Documentation

Access the interactive API documentation at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI Docs**: `http://localhost:8080/v3/api-docs`

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Tasks
- `GET /api/tasks` - Get all tasks
- `POST /api/tasks` - Create a new task
- `GET /api/tasks/{id}` - Get task by ID
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

## Usage Example

### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user@example.com",
    "password": "password123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user@example.com",
    "password": "password123"
  }'
```

### Create a task (requires JWT token)
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "title": "Sample Task",
    "description": "This is a sample task",
    "completed": false
  }'
```

## Testing

Run the tests using Maven:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
```

This README provides a comprehensive overview of your Taskido project, including setup instructions, API documentation, and usage examples. You may want to customize the repository URL, license information, and any specific details about your implementation.
