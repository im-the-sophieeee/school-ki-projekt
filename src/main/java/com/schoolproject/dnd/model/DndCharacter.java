package com.schoolproject.dnd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entity representing a D&D Character.
 * Contains all the basic attributes for a character including
 * name, race, class, level, and ability scores.
 */
@Entity
@Table(name = "characters")
public class DndCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Race is required")
    @Column(nullable = false)
    private String race;

    @NotBlank(message = "Character class is required")
    @Column(name = "character_class", nullable = false)
    private String characterClass;

    @Min(value = 1, message = "Level must be at least 1")
    @Max(value = 20, message = "Level cannot exceed 20")
    @Column(nullable = false)
    private int level = 1;

    @Min(value = 1, message = "Strength must be at least 1")
    @Max(value = 20, message = "Strength cannot exceed 20")
    private int strength = 10;

    @Min(value = 1, message = "Dexterity must be at least 1")
    @Max(value = 20, message = "Dexterity cannot exceed 20")
    private int dexterity = 10;

    @Min(value = 1, message = "Constitution must be at least 1")
    @Max(value = 20, message = "Constitution cannot exceed 20")
    private int constitution = 10;

    @Min(value = 1, message = "Intelligence must be at least 1")
    @Max(value = 20, message = "Intelligence cannot exceed 20")
    private int intelligence = 10;

    @Min(value = 1, message = "Wisdom must be at least 1")
    @Max(value = 20, message = "Wisdom cannot exceed 20")
    private int wisdom = 10;

    @Min(value = 1, message = "Charisma must be at least 1")
    @Max(value = 20, message = "Charisma cannot exceed 20")
    private int charisma = 10;

    @Size(max = 1000, message = "Background cannot exceed 1000 characters")
    @Column(length = 1000)
    private String background;

    // Constructors
    public DndCharacter() {
    }

    public DndCharacter(String name, String race, String characterClass) {
        this.name = name;
        this.race = race;
        this.characterClass = characterClass;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    // Helper methods for ability score modifiers (D&D 5e formula: floor((score - 10) / 2))
    private int calculateModifier(int score) {
        return (int) Math.floor((score - 10) / 2.0);
    }

    private String formatModifier(int modifier) {
        return modifier >= 0 ? "+" + modifier : String.valueOf(modifier);
    }

    public String getStrengthModifier() {
        return formatModifier(calculateModifier(strength));
    }

    public String getDexterityModifier() {
        return formatModifier(calculateModifier(dexterity));
    }

    public String getConstitutionModifier() {
        return formatModifier(calculateModifier(constitution));
    }

    public String getIntelligenceModifier() {
        return formatModifier(calculateModifier(intelligence));
    }

    public String getWisdomModifier() {
        return formatModifier(calculateModifier(wisdom));
    }

    public String getCharismaModifier() {
        return formatModifier(calculateModifier(charisma));
    }

    @Override
    public String toString() {
        return "DndCharacter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", characterClass='" + characterClass + '\'' +
                ", level=" + level +
                '}';
    }
}
