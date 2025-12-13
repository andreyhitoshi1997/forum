# Forum Application

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.25-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)

A modern, scalable **AI-powered clinic assistant forum** built with Spring Boot and Kotlin. This application provides a robust RESTful API for managing forum topics, user discussions, and course-related queries with extensible AI integration capabilities using LangChain4j.

## Table of Contents

- [Key Features](#key-features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Architecture](#architecture)
- [API Documentation](#api-documentation)
- [Development](#development)
- [Troubleshooting](#troubleshooting)
- [License](#license)
- [Contact & Support](#contact--support)

## Key Features

- 🚀 **RESTful API** - Clean and intuitive REST endpoints for managing forum topics and discussions
- 👥 **User Management** - Support for user authentication and profile management
- 💬 **Topic Management** - Create, read, and manage forum topics with threaded responses
- 📚 **Course Integration** - Topics linked to specific courses for better organization
- ✅ **Status Tracking** - Track topic resolution status (Respondido/Não Respondido)
- 🤖 **AI-Ready Architecture** - Extensible design for LangChain4j integration
- 🔄 **Request/Response DTO Pattern** - Clean separation of concerns with data transfer objects
- 📝 **Validation** - Built-in request validation
- 🛠️ **Development Tools** - Integrated hot-reload support with Spring Boot DevTools

## Prerequisites

Before you begin, ensure you have the following installed:

### Required
- **Java 21+** - [Download Java](https://www.oracle.com/java/technologies/downloads/#java21)
- **Gradle 8.0+** - [Download Gradle](https://gradle.org/install/) (or use the included Gradle wrapper)

### Optional but Recommended
- **Git** - [Download Git](https://git-scm.com/downloads)
- **IDE** - IntelliJ IDEA, VS Code, or any Kotlin-compatible IDE
- **OpenAI API Key** - For future AI integration features (optional)

### System Requirements
- RAM: 4GB minimum (8GB recommended)
- Disk Space: 2GB for dependencies and build artifacts
- OS: macOS, Linux, or Windows with WSL2

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/andreyhitoshi1997/forum.git
cd forum
```

### 2. Configure Development Environment

For detailed setup instructions including environment variables and database configuration, see [SETUP.md](./SETUP.md).

Quick start:
```bash
# Copy environment template
cp .env.example .env

# Edit with your local credentials
nano .env

# Run setup script (optional but recommended)
chmod +x setup-dev.sh
./setup-dev.sh
```

### 3. Verify Java Installation

```bash
java -version
# Expected output: java version "21" or higher
```

### 4. Build the Project

```bash
./gradlew clean build
```

On Windows, use:
```bash
gradlew.bat clean build
```

### 5. Verify the Build

```bash
./gradlew -v
# Should display Gradle version and build configuration
```

### Environment Variables (Optional)

For AI features and future integrations, set up environment variables:

**macOS/Linux:**
```bash
export OPENAI_API_KEY=your_openai_api_key_here
export FORUM_ENV=development
export FORUM_DEBUG=true
```

**Windows:**
```cmd
set OPENAI_API_KEY=your_openai_api_key_here
set FORUM_ENV=development
set FORUM_DEBUG=true
```

## Configuration

### ⚠️ Security: Managing API Keys and Secrets

**IMPORTANT: Never commit API keys or secrets to the repository!**

Always use environment variables for sensitive information:

```bash
# macOS/Linux - Set environment variables before running the app
export AI_API_KEY=your-actual-key
export XAI_API_KEY=your-actual-key
export OPENAI_API_KEY=your-actual-key
./gradlew bootRun

# Windows - Use set command
set AI_API_KEY=your-actual-key
set XAI_API_KEY=your-actual-key
set OPENAI_API_KEY=your-actual-key
gradlew.bat bootRun
```

For detailed security practices, see [SECURITY.md](./SECURITY.md).

### Application Properties

Edit `src/main/resources/application.properties` to configure the application:

```properties
# Server Configuration
spring.application.name=forum
server.port=8080
server.servlet.context-path=/api

# Environment
spring.profiles.active=development

# Logging
logging.level.root=INFO
logging.level.dev.andrey.forum=DEBUG

# Database (when configured)
# spring.datasource.url=jdbc:mysql://localhost:3306/forum
# spring.datasource.username=root
# spring.datasource.password=password
# spring.jpa.hibernate.ddl-auto=update

# AI/LangChain4j Configuration (Future)
# ai.model.type=openai
# ai.model.api-key=${OPENAI_API_KEY}
```

### Required Properties Explained

| Property | Description | Example |
|----------|-------------|---------|
| `spring.application.name` | Application identifier | `forum` |
| `server.port` | Server port | `8080` |
| `spring.profiles.active` | Active profile (development/production) | `development` |

### Environment Variables

Set these in your system or `.env` file:

```bash
OPENAI_API_KEY=sk-... # For AI features
FORUM_ENV=development # development, staging, production
FORUM_DEBUG=true      # Enable debug logging
```

## Usage

### Running the Application

#### Development Mode (with hot-reload)

```bash
./gradlew bootRun
```

The application will start at `http://localhost:8080`

#### Production Build

```bash
./gradlew build
java -jar build/libs/forum-0.0.1-SNAPSHOT.jar
```

### Health Check

Verify the application is running:

```bash
curl http://localhost:8080/hello
```

**Response:**
```
Hello World!
```

### Authentication

This application uses **HTTP Basic Authentication** with Spring Security.

#### Default Test Credentials

- **Username (Email):** `usuario@teste.com`
- **Password:** `senha123`
- **User ID:** `1`
- **Role:** `LEITURA_ESCRITA`

#### How to Authenticate

**Option 1: Using Authorization Header (Basic Auth)**

```bash
# The header value is: Base64(username:password)
# Base64(usuario@teste.com:senha123) = dXN1YXJpb0B0ZXN0ZS5jb206c2VuaGExMjM=

curl -X GET http://localhost:8081/topicos \
  -H "Authorization: Basic dXN1YXJpb0B0ZXN0ZS5jb206c2VuaGExMjM="
```

**Option 2: Using curl with --user flag**

```bash
curl -X GET http://localhost:8081/topicos \
  --user usuario@teste.com:senha123
```

**Option 3: Using Postman**
1. Select the request
2. Go to **Authorization** tab
3. Choose **Basic Auth**
4. Enter:
   - Username: `usuario@teste.com`
   - Password: `senha123`

**Option 4: Using JavaScript/Fetch**

```javascript
const auth = btoa('usuario@teste.com:senha123');
fetch('http://localhost:8081/topicos', {
  headers: {
    'Authorization': `Basic ${auth}`
  }
})
.then(r => r.json())
.then(data => console.log(data));
```

**Option 5: Using Python**

```python
import requests
from requests.auth import HTTPBasicAuth

response = requests.get(
    'http://localhost:8081/topicos',
    auth=HTTPBasicAuth('usuario@teste.com', 'senha123')
)
print(response.json())
```

For more details, see [AUTENTICACAO.md](./AUTENTICACAO.md).

---

### Available Endpoints

#### 1. Health/Hello Endpoint

**GET** `/hello`

Check if the application is running.

**Response:**
```json
"Hello World!"
```

---

#### 2. List All Topics

**GET** `/topicos`

Retrieve all forum topics.

**Response:**
```json
[
  {
    "id": 1,
    "titulo": "Como usar Spring Boot?",
    "mensagem": "Qual é a melhor forma de configurar Spring Boot?",
    "dataCriacao": "2025-11-27T10:30:00",
    "status": "NAO_RESPONDIDO",
    "curso": {
      "id": 1,
      "nome": "Spring Boot Basics"
    },
    "autor": {
      "id": 1,
      "nome": "João Silva",
      "email": "joao@example.com"
    },
    "respostas": []
  }
]
```

---

#### 3. Get Topic by ID

**GET** `/topicos/{id}`

Retrieve a specific topic by its ID.

**Path Parameters:**
- `id` (Long) - Topic ID

**Example:**
```bash
curl http://localhost:8080/topicos/1
```

**Response:**
```json
{
  "id": 1,
  "titulo": "Como usar Spring Boot?",
  "mensagem": "Qual é a melhor forma de configurar Spring Boot?",
  "dataCriacao": "2025-11-27T10:30:00",
  "status": "NAO_RESPONDIDO",
  "curso": {
    "id": 1,
    "nome": "Spring Boot Basics"
  },
  "autor": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com"
  },
  "respostas": []
}
```

---

#### 4. Create New Topic

**POST** `/topicos`

Create a new forum topic.

**Request Body:**
```json
{
  "titulo": "Como usar Spring Boot?",
  "mensagem": "Qual é a melhor forma de configurar Spring Boot?",
  "cursoId": 1,
  "autorId": 1
}
```

**Response:**
```json
[
  {
    "id": 2,
    "titulo": "Como usar Spring Boot?",
    "mensagem": "Qual é a melhor forma de configurar Spring Boot?",
    "dataCriacao": "2025-11-27T11:00:00",
    "status": "NAO_RESPONDIDO",
    "curso": {
      "id": 1,
      "nome": "Spring Boot Basics"
    },
    "autor": {
      "id": 1,
      "nome": "João Silva",
      "email": "joao@example.com"
    },
    "respostas": []
  }
]
```

---

### Example API Requests

#### Using cURL

```bash
# List topics
curl -X GET http://localhost:8080/topicos

# Get specific topic
curl -X GET http://localhost:8080/topicos/1

# Create new topic
curl -X POST http://localhost:8080/topicos \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Spring Boot Question",
    "mensagem": "How to configure Spring Boot?",
    "cursoId": 1,
    "autorId": 1
  }'
```

#### Using Postman

1. Import the endpoints into Postman
2. Set base URL: `http://localhost:8080`
3. Create requests for each endpoint
4. Save as collection for team sharing

#### Using Python

```python
import requests

base_url = "http://localhost:8080"

# Get all topics
response = requests.get(f"{base_url}/topicos")
topics = response.json()

# Create new topic
new_topic = {
    "titulo": "Spring Boot Best Practices",
    "mensagem": "What are the best practices?",
    "cursoId": 1,
    "autorId": 1
}
response = requests.post(f"{base_url}/topicos", json=new_topic)
```

### Error Handling

The API returns standard HTTP status codes:

| Code | Meaning | Example |
|------|---------|---------|
| 200 | OK - Request succeeded | Successfully retrieved topics |
| 201 | Created - Resource created | Topic created successfully |
| 400 | Bad Request - Invalid input | Missing required fields |
| 404 | Not Found - Resource not found | Topic ID doesn't exist |
| 500 | Server Error | Unexpected server error |

**Error Response Format:**
```json
{
  "timestamp": "2025-11-27T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Campo 'titulo' é obrigatório",
  "path": "/topicos"
}
```

## Architecture

### Project Structure

```
forum/
├── src/
│   ├── main/
│   │   ├── kotlin/dev/andrey/forum/
│   │   │   ├── ForumApplication.kt          # Spring Boot entry point
│   │   │   ├── controller/                  # REST endpoints
│   │   │   │   ├── HelloContorller.kt       # Health check endpoint
│   │   │   │   └── TopicoController.kt      # Topic management endpoints
│   │   │   ├── service/                     # Business logic layer
│   │   │   │   ├── TopicoService.kt         # Topic operations
│   │   │   │   ├── UsuarioService.kt        # User operations
│   │   │   │   └── CursoService.kt          # Course operations
│   │   │   ├── model/                       # Domain models
│   │   │   │   ├── Topico.kt                # Topic model
│   │   │   │   ├── Usuario.kt               # User model
│   │   │   │   ├── Curso.kt                 # Course model
│   │   │   │   ├── Resposta.kt              # Response model
│   │   │   │   └── StatusTopico.kt          # Status enum
│   │   │   ├── dto/                         # Data Transfer Objects
│   │   │   │   ├── NovoTopicoForm.kt        # Create topic request
│   │   │   │   └── TopicoView.kt            # Topic response
│   │   │   ├── mapper/                      # DTO mappers
│   │   │   └── repository/                  # Data access layer
│   │   └── resources/
│   │       ├── application.properties       # Configuration
│   │       ├── static/                      # Static files
│   │       └── templates/                   # View templates
│   └── test/
│       └── kotlin/dev/andrey/forum/         # Unit and integration tests
├── build.gradle                             # Gradle build configuration
├── settings.gradle                          # Gradle settings
└── README.md                                # This file
```

### Architecture Layers

```
┌─────────────────────────────────────┐
│  REST Controller Layer               │
│  (HelloContorller, TopicoController) │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│  Service Layer                       │
│  (TopicoService, UsuarioService)     │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│  Repository Layer                    │
│  (Data Access Objects)               │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│  Database Layer                      │
│  (MySQL, PostgreSQL, etc.)           │
└─────────────────────────────────────┘
```

### Key Components

#### Controllers (`controller/`)

Handle HTTP requests and route them to services.

- **TopicoController** - Manages forum topic endpoints
  - `GET /topicos` - List all topics
  - `GET /topicos/{id}` - Get topic details
  - `POST /topicos` - Create new topic

- **HelloContorller** - Health check endpoint
  - `GET /hello` - Application status

#### Services (`service/`)

Contain business logic and orchestrate operations.

- **TopicoService** - Topic operations (CRUD, filtering, sorting)
- **UsuarioService** - User management operations
- **CursoService** - Course management operations

#### Models (`model/`)

Domain entities representing core business concepts.

- **Topico** - Forum topic with title, message, status
- **Usuario** - User with name and email
- **Curso** - Course information
- **Resposta** - Response/Reply to a topic
- **StatusTopico** - Enum for topic status (NAO_RESPONDIDO, RESPONDIDO)

#### DTOs (`dto/`)

Data Transfer Objects for API requests/responses.

- **NovoTopicoForm** - Request DTO for creating topics
- **TopicoView** - Response DTO for topic data

### Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Language | Kotlin | 1.9.25 |
| Framework | Spring Boot | 3.5.7 |
| Build Tool | Gradle | 8.0+ |
| Java | Java | 21+ |
| JSON Processing | Jackson | Included |
| Validation | Spring Validation | Included |
| Testing | JUnit 5 | Included |

### Future LangChain4j Integration

The architecture is designed to easily integrate LangChain4j for AI features:

```kotlin
// Planned AI service
@Service
class AIAssistantService(
    private val langChain: LangChain4jClient
) {
    fun getAIResponse(topic: Topico): String {
        // Use LangChain4j to generate AI responses
        return langChain.chat(topic.mensagem)
    }
}

// Integration point in TopicoController
@PostMapping("/{id}/ai-response")
fun getAIAssistance(@PathVariable id: Long): String {
    return aiService.getAIResponse(topicoService.buscarPorId(id))
}
```

## API Documentation

### Request/Response Examples

#### Create Topic Request Example

```bash
curl -X POST http://localhost:8080/topicos \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "How to configure database in Spring Boot?",
    "mensagem": "I need help setting up my MySQL database connection",
    "cursoId": 1,
    "autorId": 1
  }'
```

#### Success Response (200/201)

```json
{
  "id": 1,
  "titulo": "How to configure database in Spring Boot?",
  "mensagem": "I need help setting up my MySQL database connection",
  "dataCriacao": "2025-11-27T14:30:00",
  "status": "NAO_RESPONDIDO",
  "curso": {
    "id": 1,
    "nome": "Spring Boot Advanced"
  },
  "autor": {
    "id": 1,
    "nome": "Jane Doe",
    "email": "jane@example.com"
  },
  "respostas": []
}
```

#### Error Response (400)

```json
{
  "timestamp": "2025-11-27T14:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Field 'titulo' is required",
  "path": "/topicos"
}
```

#### Error Response (404)

```json
{
  "timestamp": "2025-11-27T14:31:00",
  "status": 404,
  "error": "Not Found",
  "message": "Topic with ID 999 not found",
  "path": "/topicos/999"
}
```

### Status Codes Reference

| Code | Message | When Used |
|------|---------|-----------|
| 200 | OK | Successful GET request |
| 201 | Created | Successful POST request |
| 204 | No Content | Successful DELETE request |
| 400 | Bad Request | Invalid input data |
| 401 | Unauthorized | Missing/invalid authentication |
| 403 | Forbidden | User doesn't have permission |
| 404 | Not Found | Resource doesn't exist |
| 500 | Internal Server Error | Server-side error |

## Development

### Running Tests

#### Run All Tests

```bash
./gradlew test
```

#### Run Specific Test Class

```bash
./gradlew test --tests dev.andrey.forum.ForumApplicationTests
```

#### Run Tests with Coverage

```bash
./gradlew test jacocoTestReport
# Coverage report: build/reports/jacoco/test/html/index.html
```

#### Run Tests in Watch Mode (continuous testing)

```bash
./gradlew test -t
```

### Code Style Guidelines

#### Kotlin Style Guidelines

This project follows [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html):

- **File naming** - Use PascalCase (e.g., `UserController.kt`)
- **Class naming** - PascalCase (e.g., `class TopicoService`)
- **Function naming** - camelCase (e.g., `fun buscarPorId()`)
- **Variable naming** - camelCase (e.g., `var topicTitle`)
- **Constants** - UPPER_SNAKE_CASE (e.g., `const val MAX_TOPICS = 100`)

#### Code Formatting

```kotlin
// Good: Clear and concise
data class Usuario (
    val id: Long,
    val nome: String,
    val email: String
)

// Good: Proper indentation
@RestController
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) {
    @GetMapping
    fun listar(): List<TopicoView> {
        return service.listar()
    }
}
```

#### Documentation

```kotlin
/**
 * Searches for a topic by ID.
 * 
 * @param id The topic ID to search for
 * @return The topic view if found
 * @throws NotFoundException if topic is not found
 */
fun buscarPorId(@PathVariable id: Long): TopicoView {
    return service.buscarPorId(id)
}
```

### How to Contribute

1. **Fork the Repository**
   ```bash
   git clone https://github.com/andreyhitoshi1997/forum.git
   cd forum
   ```

2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/my-new-feature
   ```

3. **Make Changes**
   - Follow the code style guidelines
   - Write tests for new features
   - Update documentation

4. **Commit Changes**
   ```bash
   git add .
   git commit -m "feat: add new feature description"
   ```

5. **Push to Branch**
   ```bash
   git push origin feature/my-new-feature
   ```

6. **Create Pull Request**
   - Go to GitHub repository
   - Click "New Pull Request"
   - Describe your changes
   - Wait for review

### Development Workflow

```bash
# 1. Start development server
./gradlew bootRun

# 2. In another terminal, run tests in watch mode
./gradlew test -t

# 3. Make code changes
# 4. Changes are automatically hot-reloaded
# 5. Tests run automatically

# 6. Before committing, run full test suite
./gradlew clean test

# 7. Build final JAR
./gradlew build
```

### Debugging

#### Enable Debug Mode

Add to `application.properties`:
```properties
logging.level.dev.andrey.forum=DEBUG
```

#### Debug with IntelliJ IDEA

1. Right-click `ForumApplication.kt`
2. Select "Debug 'ForumApplication'"
3. Set breakpoints and inspect variables

#### Debug with Remote Debugger

```bash
./gradlew bootRun --args='--debug'
```

Then attach your IDE debugger to port 5005.

## Troubleshooting

### Common Issues and Solutions

#### Issue 1: Build Fails - "Java 21 not found"

**Error Message:**
```
ERROR: Could not find Java toolchain matching version: 21
```

**Solution:**
```bash
# Install Java 21
# macOS:
brew install openjdk@21

# Linux:
sudo apt-get install openjdk-21-jdk

# Windows:
# Download from https://www.oracle.com/java/technologies/downloads/

# Set JAVA_HOME
export JAVA_HOME=/path/to/java21
./gradlew clean build
```

#### Issue 2: Port 8080 Already in Use

**Error Message:**
```
Port 8080 is already in use
```

**Solution:**
```bash
# Option 1: Kill the process using the port
lsof -i :8080
kill -9 <PID>

# Option 2: Use a different port
./gradlew bootRun --args='--server.port=8081'
```

#### Issue 3: Gradle Build Hangs

**Error Message:**
```
BUILD still running...
```

**Solution:**
```bash
# 1. Stop the build (Ctrl+C)
# 2. Clear Gradle cache
./gradlew clean --refresh-dependencies

# 3. Try again
./gradlew build
```

#### Issue 4: Tests Failing with "Connection Refused"

**Error Message:**
```
Connection refused: cannot connect to database
```

**Solution:**
```bash
# Check if database is configured
# Edit application.properties and verify database settings

# For in-memory testing:
# The tests should work without external database

# Run tests with:
./gradlew test
```

#### Issue 5: Lombok Annotation Processor Error

**Error Message:**
```
Cannot find symbol @Getter, @Setter, @Data
```

**Solution:**
```bash
# Rebuild with annotation processing
./gradlew clean build

# If using IDE:
# - Enable annotation processing in IDE settings
# - Invalidate caches and restart
```

### Performance Optimization

#### Slow Build Times

```bash
# 1. Enable parallel builds
./gradlew build --parallel

# 2. Enable build cache
./gradlew build --build-cache

# 3. Increase heap size for Gradle
export GRADLE_OPTS="-Xmx2g"
./gradlew build
```

#### Memory Issues at Runtime

```bash
# Increase JVM heap size
java -Xmx1g -Xms512m -jar build/libs/forum-0.0.1-SNAPSHOT.jar
```

### Getting Help

1. **Check the Logs**
   ```bash
   # Check application logs
   tail -f logs/application.log
   ```

2. **Enable Debug Logging**
   ```properties
   logging.level.dev.andrey.forum=DEBUG
   logging.level.org.springframework=DEBUG
   ```

3. **Check Dependencies**
   ```bash
   ./gradlew dependencies
   ```

4. **Clean and Rebuild**
   ```bash
   ./gradlew clean build --refresh-dependencies
   ```

## License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

### MIT License Summary

You are free to:
- ✅ Use, modify, and distribute this software
- ✅ Use it for commercial purposes
- ✅ Use it for private purposes

You must:
- ⚠️ Include the license and copyright notice
- ⚠️ Provide a copy of the license with the software

For the full license text, see the LICENSE file in the repository.

## Contact & Support

### How to Get Help

- **Documentation** - Check this README first
- **Issues** - [GitHub Issues](https://github.com/andreyhitoshi1997/forum/issues)
- **Discussions** - [GitHub Discussions](https://github.com/andreyhitoshi1997/forum/discussions)

### Contact Information

**Author:** Andrey Hitoshi

- **Email:** andreyhitoshi1997@gmail.com
- **GitHub:** [@andreyhitoshi1997](https://github.com/andreyhitoshi1997)
- **LinkedIn:** [Connect on LinkedIn](https://linkedin.com)

### Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Spring Framework Guide](https://spring.io/guides)
- [RESTful API Best Practices](https://restfulapi.net/)
- [LangChain4j Documentation](https://github.com/langchain4j/langchain4j) (for future AI features)

### Support Channels

1. **GitHub Issues** - Report bugs and request features
2. **GitHub Discussions** - Ask questions and share ideas
3. **Pull Requests** - Contribute code improvements
4. **Email** - Contact for urgent matters

---

**Last Updated:** November 27, 2025

**Version:** 0.0.1-SNAPSHOT

Made with ❤️ by Andrey Hitoshi

