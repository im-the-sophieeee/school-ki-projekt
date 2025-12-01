package com.schoolproject.dnd.service;

import com.schoolproject.dnd.model.DndCharacter;
import com.schoolproject.dnd.repository.DndCharacterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DndCharacterService.
 */
@ExtendWith(MockitoExtension.class)
class DndCharacterServiceTest {

    @Mock
    private DndCharacterRepository repository;

    @InjectMocks
    private DndCharacterService characterService;

    private DndCharacter testCharacter;

    @BeforeEach
    void setUp() {
        testCharacter = new DndCharacter("TestHero", "Human", "Fighter");
        testCharacter.setId(1L);
        testCharacter.setLevel(5);
    }

    @Test
    void getAllCharacters_ReturnsAllCharacters() {
        List<DndCharacter> characters = Arrays.asList(testCharacter);
        when(repository.findAll()).thenReturn(characters);

        List<DndCharacter> result = characterService.getAllCharacters();

        assertEquals(1, result.size());
        assertEquals("TestHero", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getCharacterById_WhenExists_ReturnsCharacter() {
        when(repository.findById(1L)).thenReturn(Optional.of(testCharacter));

        Optional<DndCharacter> result = characterService.getCharacterById(1L);

        assertTrue(result.isPresent());
        assertEquals("TestHero", result.get().getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getCharacterById_WhenNotExists_ReturnsEmpty() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<DndCharacter> result = characterService.getCharacterById(999L);

        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void saveCharacter_SavesAndReturnsCharacter() {
        when(repository.save(any(DndCharacter.class))).thenReturn(testCharacter);

        DndCharacter result = characterService.saveCharacter(testCharacter);

        assertEquals("TestHero", result.getName());
        verify(repository, times(1)).save(testCharacter);
    }

    @Test
    void deleteCharacter_CallsRepositoryDelete() {
        doNothing().when(repository).deleteById(1L);

        characterService.deleteCharacter(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void findByRace_ReturnsMatchingCharacters() {
        when(repository.findByRace("Human")).thenReturn(Arrays.asList(testCharacter));

        List<DndCharacter> result = characterService.findByRace("Human");

        assertEquals(1, result.size());
        assertEquals("Human", result.get(0).getRace());
    }

    @Test
    void findByCharacterClass_ReturnsMatchingCharacters() {
        when(repository.findByCharacterClass("Fighter")).thenReturn(Arrays.asList(testCharacter));

        List<DndCharacter> result = characterService.findByCharacterClass("Fighter");

        assertEquals(1, result.size());
        assertEquals("Fighter", result.get(0).getCharacterClass());
    }

    @Test
    void getAvailableRaces_ReturnsRaceList() {
        List<String> races = characterService.getAvailableRaces();

        assertNotNull(races);
        assertFalse(races.isEmpty());
        assertTrue(races.contains("Human"));
        assertTrue(races.contains("Elf"));
        assertTrue(races.contains("Dwarf"));
    }

    @Test
    void getAvailableClasses_ReturnsClassList() {
        List<String> classes = characterService.getAvailableClasses();

        assertNotNull(classes);
        assertFalse(classes.isEmpty());
        assertTrue(classes.contains("Fighter"));
        assertTrue(classes.contains("Wizard"));
        assertTrue(classes.contains("Rogue"));
    }

    @Test
    void generateRandomCharacter_CreatesValidCharacter() {
        DndCharacter randomCharacter = characterService.generateRandomCharacter();

        assertNotNull(randomCharacter);
        assertNotNull(randomCharacter.getName());
        assertNotNull(randomCharacter.getRace());
        assertNotNull(randomCharacter.getCharacterClass());
        assertTrue(randomCharacter.getLevel() >= 1 && randomCharacter.getLevel() <= 10);
        assertTrue(randomCharacter.getStrength() >= 3 && randomCharacter.getStrength() <= 18);
        assertTrue(randomCharacter.getDexterity() >= 3 && randomCharacter.getDexterity() <= 18);
        assertTrue(randomCharacter.getConstitution() >= 3 && randomCharacter.getConstitution() <= 18);
        assertTrue(randomCharacter.getIntelligence() >= 3 && randomCharacter.getIntelligence() <= 18);
        assertTrue(randomCharacter.getWisdom() >= 3 && randomCharacter.getWisdom() <= 18);
        assertTrue(randomCharacter.getCharisma() >= 3 && randomCharacter.getCharisma() <= 18);
        assertNotNull(randomCharacter.getBackground());
    }

    @Test
    void generateRandomCharacter_UsesValidRaceAndClass() {
        List<String> validRaces = characterService.getAvailableRaces();
        List<String> validClasses = characterService.getAvailableClasses();

        for (int i = 0; i < 10; i++) {
            DndCharacter randomCharacter = characterService.generateRandomCharacter();
            assertTrue(validRaces.contains(randomCharacter.getRace()));
            assertTrue(validClasses.contains(randomCharacter.getCharacterClass()));
        }
    }
}
