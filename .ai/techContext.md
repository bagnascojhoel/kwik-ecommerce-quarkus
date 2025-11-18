# Technical Context

## Technology Stack

### Core Framework

- **Quarkus 3.x**: Supersonic Subatomic Java Framework
  - Fast startup time
  - Low memory footprint
  - Native compilation support (GraalVM)
  - Dev mode with live reload

### Language & Runtime

- **Java 21**: Latest LTS version
  - Modern language features
  - Pattern matching
  - Records (if needed)
  - Virtual threads support

### Build System

- **Gradle 8.11**: Build automation
  - Kotlin DSL (build.gradle)
  - Dependency management
  - Multi-module support
  - Custom tasks for code quality

### Database

- **SQLite**: Embedded database
  - File-based: `kwik.db`
  - Zero configuration
  - Ideal for development
  - Simple multi-tenancy implementation
- **Liquibase**: Database migration tool
  - Version control for schema
  - Migrations in `src/main/resources/migrations/`
  - Organized by module (auth/, product/, tenant/)

### Persistence Layer

- **Hibernate ORM with Panache**: JPA abstraction
  - Active Record pattern
  - Repository pattern support
  - Type-safe queries
  - `PanacheEntityBase`, `PanacheRepository`

### Security

- **JWT (JSON Web Tokens)**: Authentication
  - `io.jsonwebtoken:jjwt-api:0.13.0`
  - Token generation and validation
  - Stateless authentication
- **BCrypt**: Password hashing
  - `at.favre.lib:bcrypt:0.10.2`
  - Secure password storage

### REST API

- **Quarkus REST (formerly RESTEasy Reactive)**: JAX-RS implementation
  - Reactive programming model
  - Non-blocking I/O
- **Jackson**: JSON serialization
  - `quarkus-rest-jackson`
- **SmallRye OpenAPI**: API documentation
  - `quarkus-smallrye-openapi`
  - Swagger UI at `/q/swagger-ui`
  - OpenAPI spec generation

### Validation

- **Hibernate Validator**: Bean validation
  - `quarkus-hibernate-validator`
  - Jakarta validation annotations
  - `@NotBlank`, `@Positive`, `@Size`, etc.

### Testing

- **JUnit 5**: Test framework
  - `quarkus-junit5`
  - `@QuarkusTest` annotation
- **REST Assured**: API testing
  - HTTP client for integration tests
- **Cucumber**: BDD testing
  - `quarkiverse-cucumber:1.3.0`
  - Gherkin feature files
- **Mockito**: Mocking framework
  - `quarkus-junit5-mockito`

### Code Quality

- **Lombok**: Boilerplate reduction
  - `io.freefair.lombok:8.11`
  - `@Value`, `@Builder`, `@Slf4j`
- **Spotless**: Code formatting
  - `com.diffplug.spotless:7.2.1`
  - Google Java Style
  - Markdown formatting
  - SQL formatting
  - Auto-apply via Gradle

### Logging

- **SLF4J**: Logging facade
  - Used via `@Slf4j` annotation
- **JBoss LogManager**: Quarkus default

## Development Environment

### Prerequisites

- **Java 21 JDK**: Required
- **Docker**: For containerization and potential future dependencies
- **Git**: Version control
- **IDE**: VSCode, IntelliJ IDEA, or Eclipse

### Local Setup

#### Initial Setup

```bash
# Clone repository
git clone <repository-url>
cd kwik-ecommerce-quarkus

# Initialize development environment
./init-for-dev.sh
```

#### Running the Application

```bash
# Development mode (with live reload)
./gradlew quarkusDev

# Access points:
# - API: http://localhost:8080
# - Dev UI: http://localhost:8080/q/dev/
# - Swagger UI: http://localhost:8080/q/swagger-ui
```

#### Building

```bash
# Compile and run tests
./gradlew build

# Run tests only
./gradlew test

# Run BDD tests
./gradlew cucumberFeatureTest

# Format code
./gradlew spotlessApply

# Complete wrap-up (format + build + test)
./gradlew assistantWrapUp
```

### Database Management

```bash
# Liquibase migrations run automatically on startup
# Manual migration (if needed):
./gradlew update

# Database file location: ./kwik.db
```

### Docker Setup

```bash
# Docker Compose available in dev-tools/
cd dev-tools
docker-compose up -d

# Includes initialization scripts
```

## Configuration

### Application Properties

Location: `src/main/resources/application.properties`

```properties
# Database
quarkus.datasource.db-kind=sqlite
quarkus.datasource.jdbc.url=jdbc:sqlite:kwik.db

# Liquibase
quarkus.liquibase.migrate-at-start=true
quarkus.liquibase.change-log=migrations/changelog.xml

# Hibernate
quarkus.hibernate-orm.log.sql=true
quarkus.hibernate-orm.validation.enabled=false

# OpenAPI
quarkus.smallrye-openapi.info-title=Kwik Ecommerce API
quarkus.smallrye-openapi.info-version=latest
quarkus.swagger-ui.try-it-out-enabled=true
```

### Gradle Configuration

#### Key Dependencies

```groovy
dependencies {
    // Quarkus core
    implementation 'io.quarkus:quarkus-arc'
    implementation 'io.quarkus:quarkus-rest-jackson'
    implementation 'io.quarkus:quarkus-hibernate-orm-panache'
    
    // Database
    implementation 'io.quarkiverse.jdbc:quarkus-jdbc-sqlite:3.0.11'
    implementation 'org.xerial:sqlite-jdbc:3.49.1.0'
    implementation 'io.quarkus:quarkus-liquibase'
    
    // Security
    implementation 'io.jsonwebtoken:jjwt-api:0.13.0'
    implementation 'at.favre.lib:bcrypt:0.10.2'
    
    // Testing
    testImplementation 'io.quarkus:quarkus-junit5'
    testImplementation 'io.rest-assured:rest-assured'
    implementation 'io.quarkiverse.cucumber:quarkus-cucumber:1.3.0'
    
    // Tools
    implementation 'io.quarkus:quarkus-smallrye-openapi'
    implementation 'io.quarkus:quarkus-hibernate-validator'
}
```

### JVM Configuration

#### Development Mode

```groovy
quarkusDev {
    jvmArgs = ["-enableassertions"]
}
```

## Project Structure

### Source Organization

```
src/
├── main/
│   ├── java/br/com/bagnascojhoel/kwik/ecommerce/
│   │   ├── auth/              # Authentication module
│   │   ├── product/           # Product module
│   │   ├── tenant/            # Tenant module
│   │   ├── common/            # Shared domain
│   │   └── infra_shared/      # Shared infrastructure
│   └── resources/
│       ├── application.properties
│       └── migrations/
│           ├── changelog.xml
│           ├── auth/
│           ├── product/
│           └── tenant/
└── test/
    ├── java/br/com/bagnascojhoel/kwik/ecommerce/
    └── resources/
        └── user-acceptance-testing/
            └── *.feature
```

### Documentation

```
docs/
├── guide-lines.md        # Development guidelines
├── memory-bank/          # AI memory system
└── (future ADRs, etc.)

.github/
├── instructions/         # Copilot instructions
└── prompts/             # AI prompts
```

### Development Tools

```
dev-tools/
├── docker-compose.yml
├── java-google-style.xml
├── githooks/
│   ├── commit-msg
│   └── pre-push
└── init-scripts/
    └── create-common-schema.sql
```

## Technical Constraints

### Performance

- Startup time: < 1 second (Quarkus dev mode)
- Memory: Low footprint (< 100MB typical)
- Database: File-based, single-user (development)

### Scalability Considerations

- SQLite: Not production-ready for high concurrency
- Future migration path to PostgreSQL/MySQL needed
- Stateless design supports horizontal scaling

### Production Packaging

```bash
# Standard JAR
./gradlew build

# Uber JAR
./gradlew build -Dquarkus.package.jar.type=uber-jar

# Native image (GraalVM required)
./gradlew build -Dquarkus.native.enabled=true

# Native in Docker
./gradlew build \
  -Dquarkus.native.enabled=true \
  -Dquarkus.native.container-build=true
```

## External Interfaces

### REST API

- Base URL: `http://localhost:8080` (development)
- OpenAPI Definition: `/q/openapi`
- Swagger UI: `/q/swagger-ui`

### Database

- Type: SQLite 3
- Driver: `org.xerial:sqlite-jdbc:3.49.1.0`
- Connection: `jdbc:sqlite:kwik.db`

## Monitoring & Observability

### Available in Dev Mode

- **Dev UI**: `http://localhost:8080/q/dev/`
  - Config editor
  - Database console
  - Arc dependency graph
  - OpenAPI viewer

### Logging Configuration

- Default: Console output
- Level: INFO (can be configured)
- Format: JBoss LogManager default

## Security Configuration

### JWT Settings

- Algorithm: HS256 (HMAC with SHA-256)
- Token generation: `io.jsonwebtoken.Jwts`
- Validation: Required for management endpoints

### Password Security

- Hashing: BCrypt
- Salt: Auto-generated per password
- Rounds: Default (typically 10)

## Known Technical Limitations

1. **SQLite Concurrency**: Limited concurrent write support
2. **No Native Auth**: Custom JWT implementation (not Quarkus Security)
3. **File Storage**: Photo URLs only, no file upload/storage yet
4. **Single Database**: All tenants in one SQLite file
5. **Development Focus**: Configuration optimized for dev, not production

