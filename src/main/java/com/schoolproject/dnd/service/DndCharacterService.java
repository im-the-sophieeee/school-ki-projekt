package com.schoolproject.dnd.service;

import com.schoolproject.dnd.model.DndCharacter;
import com.schoolproject.dnd.repository.DndCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for D&D Character operations.
 * Contains business logic for character management and generation.
 */
@Service
public class DndCharacterService {

    private final DndCharacterRepository repository;
    private final Random random = new Random();

    // D&D Races
    private static final List<String> RACES = Arrays.asList(
            "Human", "Elf", "Dwarf", "Halfling", "Dragonborn",
            "Gnome", "Half-Elf", "Half-Orc", "Tiefling"
    );

    // D&D Classes
    private static final List<String> CLASSES = Arrays.asList(
            "Barbarian", "Bard", "Cleric", "Druid", "Fighter",
            "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer",
            "Warlock", "Wizard"
    );

    // Name prefixes for random generation
    private static final List<String> NAME_PREFIXES = Arrays.asList(
            "Thorn", "Shadow", "Storm", "Iron", "Silver", "Dark", "Light",
            "Fire", "Ice", "Stone", "Wind", "Thunder", "Moon", "Sun"
    );

    // Name suffixes for random generation
    private static final List<String> NAME_SUFFIXES = Arrays.asList(
            "blade", "heart", "striker", "walker", "seeker", "bringer",
            "warden", "keeper", "slayer", "hunter", "weaver", "caller"
    );

    @Autowired
    public DndCharacterService(DndCharacterRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all characters.
     */
    public List<DndCharacter> getAllCharacters() {
        return repository.findAll();
    }

    /**
     * Get character by ID.
     */
    public Optional<DndCharacter> getCharacterById(Long id) {
        return repository.findById(id);
    }

    /**
     * Save a character.
     */
    public DndCharacter saveCharacter(DndCharacter character) {
        return repository.save(character);
    }

    /**
     * Delete a character by ID.
     */
    public void deleteCharacter(Long id) {
        repository.deleteById(id);
    }

    /**
     * Find characters by race.
     */
    public List<DndCharacter> findByRace(String race) {
        return repository.findByRace(race);
    }

    /**
     * Find characters by class.
     */
    public List<DndCharacter> findByCharacterClass(String characterClass) {
        return repository.findByCharacterClass(characterClass);
    }

    /**
     * Search characters by name.
     */
    public List<DndCharacter> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Get available races.
     */
    public List<String> getAvailableRaces() {
        return RACES;
    }

    /**
     * Get available classes.
     */
    public List<String> getAvailableClasses() {
        return CLASSES;
    }

    /**
     * Generate a random character.
     */
    public DndCharacter generateRandomCharacter() {
        DndCharacter character = new DndCharacter();
        
        // Generate random name
        character.setName(generateRandomName());
        
        // Random race and class
        character.setRace(RACES.get(random.nextInt(RACES.size())));
        character.setCharacterClass(CLASSES.get(random.nextInt(CLASSES.size())));
        
        // Random level (1-10 for new characters)
        character.setLevel(random.nextInt(10) + 1);
        
        // Generate ability scores using 4d6 drop lowest method
        character.setStrength(rollAbilityScore());
        character.setDexterity(rollAbilityScore());
        character.setConstitution(rollAbilityScore());
        character.setIntelligence(rollAbilityScore());
        character.setWisdom(rollAbilityScore());
        character.setCharisma(rollAbilityScore());
        
        // Generate background
        character.setBackground(generateBackground(character));
        
        return character;
    }

    /**
     * Generate a random name.
     */
    private String generateRandomName() {
        String prefix = NAME_PREFIXES.get(random.nextInt(NAME_PREFIXES.size()));
        String suffix = NAME_SUFFIXES.get(random.nextInt(NAME_SUFFIXES.size()));
        return prefix + suffix;
    }

    /**
     * Roll an ability score using 4d6 drop lowest method.
     */
    private int rollAbilityScore() {
        int[] rolls = new int[4];
        for (int i = 0; i < 4; i++) {
            rolls[i] = random.nextInt(6) + 1;
        }
        Arrays.sort(rolls);
        // Sum the top 3 dice
        return rolls[1] + rolls[2] + rolls[3];
    }

    /**
     * Generate a simple background story.
     */
    private String generateBackground(DndCharacter character) {
        String[] origins = {"a small village", "a bustling city", "a nomadic tribe", "a secluded monastery", "a noble house"};
        String[] motivations = {"seeks glory", "searches for lost family", "wants revenge", "desires knowledge", "pursues justice"};
        
        String origin = origins[random.nextInt(origins.length)];
        String motivation = motivations[random.nextInt(motivations.length)];
        
        return String.format("%s is a %s %s from %s who %s.", 
                character.getName(), 
                character.getRace(), 
                character.getCharacterClass(), 
                origin, 
                motivation);
    }
}
