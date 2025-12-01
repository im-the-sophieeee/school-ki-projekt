package com.schoolproject.dnd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolproject.dnd.model.DndCharacter;
import com.schoolproject.dnd.service.DndCharacterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for CharacterRestController.
 */
@WebMvcTest(CharacterRestController.class)
class CharacterRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DndCharacterService characterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCharacters_ReturnsListOfCharacters() throws Exception {
        DndCharacter character1 = createTestCharacter(1L, "Thorin", "Dwarf", "Fighter");
        DndCharacter character2 = createTestCharacter(2L, "Legolas", "Elf", "Ranger");
        List<DndCharacter> characters = Arrays.asList(character1, character2);

        when(characterService.getAllCharacters()).thenReturn(characters);

        mockMvc.perform(get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Thorin"))
                .andExpect(jsonPath("$[1].name").value("Legolas"));

        verify(characterService, times(1)).getAllCharacters();
    }

    @Test
    void getCharacterById_WhenExists_ReturnsCharacter() throws Exception {
        DndCharacter character = createTestCharacter(1L, "Gandalf", "Human", "Wizard");
        when(characterService.getCharacterById(1L)).thenReturn(Optional.of(character));

        mockMvc.perform(get("/api/characters/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gandalf"))
                .andExpect(jsonPath("$.race").value("Human"))
                .andExpect(jsonPath("$.characterClass").value("Wizard"));

        verify(characterService, times(1)).getCharacterById(1L);
    }

    @Test
    void getCharacterById_WhenNotExists_ReturnsNotFound() throws Exception {
        when(characterService.getCharacterById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/characters/999"))
                .andExpect(status().isNotFound());

        verify(characterService, times(1)).getCharacterById(999L);
    }

    @Test
    void createCharacter_WithValidData_ReturnsCreated() throws Exception {
        DndCharacter inputCharacter = new DndCharacter("Aragorn", "Human", "Ranger");
        inputCharacter.setLevel(10);
        
        DndCharacter savedCharacter = createTestCharacter(1L, "Aragorn", "Human", "Ranger");
        savedCharacter.setLevel(10);

        when(characterService.saveCharacter(any(DndCharacter.class))).thenReturn(savedCharacter);

        mockMvc.perform(post("/api/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputCharacter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Aragorn"));

        verify(characterService, times(1)).saveCharacter(any(DndCharacter.class));
    }

    @Test
    void deleteCharacter_WhenExists_ReturnsNoContent() throws Exception {
        DndCharacter character = createTestCharacter(1L, "Frodo", "Halfling", "Rogue");
        when(characterService.getCharacterById(1L)).thenReturn(Optional.of(character));
        doNothing().when(characterService).deleteCharacter(1L);

        mockMvc.perform(delete("/api/characters/1"))
                .andExpect(status().isNoContent());

        verify(characterService, times(1)).deleteCharacter(1L);
    }

    @Test
    void deleteCharacter_WhenNotExists_ReturnsNotFound() throws Exception {
        when(characterService.getCharacterById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/characters/999"))
                .andExpect(status().isNotFound());

        verify(characterService, never()).deleteCharacter(any());
    }

    @Test
    void generateRandomCharacter_ReturnsCreatedCharacter() throws Exception {
        DndCharacter randomCharacter = createTestCharacter(1L, "Shadowblade", "Tiefling", "Warlock");
        when(characterService.generateRandomCharacter()).thenReturn(randomCharacter);
        when(characterService.saveCharacter(any(DndCharacter.class))).thenReturn(randomCharacter);

        mockMvc.perform(post("/api/characters/generate"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Shadowblade"));

        verify(characterService, times(1)).generateRandomCharacter();
        verify(characterService, times(1)).saveCharacter(any(DndCharacter.class));
    }

    @Test
    void getOptions_ReturnsRacesAndClasses() throws Exception {
        List<String> races = Arrays.asList("Human", "Elf", "Dwarf");
        List<String> classes = Arrays.asList("Fighter", "Wizard", "Rogue");

        when(characterService.getAvailableRaces()).thenReturn(races);
        when(characterService.getAvailableClasses()).thenReturn(classes);

        mockMvc.perform(get("/api/characters/options"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.races[0]").value("Human"))
                .andExpect(jsonPath("$.classes[0]").value("Fighter"));

        verify(characterService, times(1)).getAvailableRaces();
        verify(characterService, times(1)).getAvailableClasses();
    }

    private DndCharacter createTestCharacter(Long id, String name, String race, String characterClass) {
        DndCharacter character = new DndCharacter(name, race, characterClass);
        character.setId(id);
        character.setLevel(1);
        character.setStrength(10);
        character.setDexterity(10);
        character.setConstitution(10);
        character.setIntelligence(10);
        character.setWisdom(10);
        character.setCharisma(10);
        return character;
    }
}
