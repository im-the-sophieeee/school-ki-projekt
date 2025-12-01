package com.schoolproject.dnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the D&D Character Generator.
 * This Spring Boot application provides REST APIs and a GUI frontend
 * for creating and managing D&D characters.
 */
@SpringBootApplication
public class DndCharacterGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DndCharacterGeneratorApplication.class, args);
    }
}
