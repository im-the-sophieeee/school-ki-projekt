package com.schoolproject.dnd.controller;

import com.schoolproject.dnd.model.DndCharacter;
import com.schoolproject.dnd.service.DndCharacterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller for D&D Character operations.
 * Provides endpoints for CRUD operations and character generation.
 */
@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = "*")
public class CharacterRestController {

    private final DndCharacterService characterService;

    @Autowired
    public CharacterRestController(DndCharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * GET /api/characters - Get all characters
     */
    @GetMapping
    public ResponseEntity<List<DndCharacter>> getAllCharacters() {
        List<DndCharacter> characters = characterService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }

    /**
     * GET /api/characters/{id} - Get character by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<DndCharacter> getCharacterById(@PathVariable Long id) {
        return characterService.getCharacterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/characters - Create a new character
     */
    @PostMapping
    public ResponseEntity<DndCharacter> createCharacter(@Valid @RequestBody DndCharacter character) {
        DndCharacter savedCharacter = characterService.saveCharacter(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);
    }

    /**
     * PUT /api/characters/{id} - Update an existing character
     */
    @PutMapping("/{id}")
    public ResponseEntity<DndCharacter> updateCharacter(@PathVariable Long id, 
                                                        @Valid @RequestBody DndCharacter character) {
        return characterService.getCharacterById(id)
                .map(existingCharacter -> {
                    character.setId(id);
                    DndCharacter updatedCharacter = characterService.saveCharacter(character);
                    return ResponseEntity.ok(updatedCharacter);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/characters/{id} - Delete a character
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        return characterService.getCharacterById(id)
                .map(character -> {
                    characterService.deleteCharacter(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/characters/generate - Generate a random character
     */
    @PostMapping("/generate")
    public ResponseEntity<DndCharacter> generateRandomCharacter() {
        DndCharacter character = characterService.generateRandomCharacter();
        DndCharacter savedCharacter = characterService.saveCharacter(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCharacter);
    }

    /**
     * GET /api/characters/search?name={name} - Search characters by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<DndCharacter>> searchCharacters(@RequestParam String name) {
        List<DndCharacter> characters = characterService.searchByName(name);
        return ResponseEntity.ok(characters);
    }

    /**
     * GET /api/characters/race/{race} - Get characters by race
     */
    @GetMapping("/race/{race}")
    public ResponseEntity<List<DndCharacter>> getCharactersByRace(@PathVariable String race) {
        List<DndCharacter> characters = characterService.findByRace(race);
        return ResponseEntity.ok(characters);
    }

    /**
     * GET /api/characters/class/{characterClass} - Get characters by class
     */
    @GetMapping("/class/{characterClass}")
    public ResponseEntity<List<DndCharacter>> getCharactersByClass(@PathVariable String characterClass) {
        List<DndCharacter> characters = characterService.findByCharacterClass(characterClass);
        return ResponseEntity.ok(characters);
    }

    /**
     * GET /api/options - Get available races and classes
     */
    @GetMapping("/options")
    public ResponseEntity<Map<String, List<String>>> getOptions() {
        Map<String, List<String>> options = new HashMap<>();
        options.put("races", characterService.getAvailableRaces());
        options.put("classes", characterService.getAvailableClasses());
        return ResponseEntity.ok(options);
    }
}
