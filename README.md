# D&D Character Generator - School AI Project

A Java Spring Boot application for creating and managing Dungeons & Dragons characters. This project was developed with AI assistance as part of a school project demonstrating pair programming with AI.

## Features

- **REST API**: Full CRUD operations for D&D characters
- **GUI Frontend**: Thymeleaf-based web interface
- **Character Generation**: Random character generation with ability scores
- **Database**: JPA with H2 (development) / MySQL (production) support

## Technology Stack

- **Java**: Version 21 (LTS)
- **Spring Boot**: 3.2.1
- **Spring Data JPA**: Database persistence
- **Thymeleaf**: Server-side templating
- **H2 Database**: In-memory database for development
- **MySQL**: Production database support
- **Maven**: Build management

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- (Optional) MySQL 8.0+ for production

### Running the Application

1. Clone the repository:
```bash
git clone https://github.com/im-the-sophieeee/school-ki-projekt.git
cd school-ki-projekt
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Open your browser and navigate to:
- **Web GUI**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
- **API**: http://localhost:8080/api/characters

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/characters` | Get all characters |
| GET | `/api/characters/{id}` | Get character by ID |
| POST | `/api/characters` | Create a new character |
| PUT | `/api/characters/{id}` | Update a character |
| DELETE | `/api/characters/{id}` | Delete a character |
| POST | `/api/characters/generate` | Generate random character |
| GET | `/api/characters/search?name={name}` | Search characters |
| GET | `/api/characters/options` | Get available races and classes |

### Running Tests

```bash
mvn test
```

## MySQL Configuration (Production)

To use MySQL instead of H2, update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dnddb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## D&D Character Attributes

- **Name**: Character name (2-100 characters)
- **Race**: Human, Elf, Dwarf, Halfling, Dragonborn, Gnome, Half-Elf, Half-Orc, Tiefling
- **Class**: Barbarian, Bard, Cleric, Druid, Fighter, Monk, Paladin, Ranger, Rogue, Sorcerer, Warlock, Wizard
- **Level**: 1-20
- **Ability Scores**: Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma (1-20)
- **Background**: Character backstory

## Project Structure

```
src/
├── main/
│   ├── java/com/schoolproject/dnd/
│   │   ├── controller/     # REST and Web controllers
│   │   ├── model/          # Entity classes
│   │   ├── repository/     # JPA repositories
│   │   └── service/        # Business logic
│   └── resources/
│       ├── static/css/     # CSS stylesheets
│       ├── templates/      # Thymeleaf templates
│       └── application.properties
└── test/                   # Unit tests
```

## License

This project is for educational purposes as part of a school AI project.