package com.schoolproject.dnd.controller;

import com.schoolproject.dnd.model.DndCharacter;
import com.schoolproject.dnd.service.DndCharacterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Web Controller for D&D Character GUI.
 * Handles Thymeleaf template rendering for the web interface.
 */
@Controller
@RequestMapping("/")
public class CharacterWebController {

    private final DndCharacterService characterService;

    @Autowired
    public CharacterWebController(DndCharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * Home page - Display all characters
     */
    @GetMapping
    public String home(Model model) {
        model.addAttribute("characters", characterService.getAllCharacters());
        model.addAttribute("pageTitle", "D&D Character Generator");
        return "index";
    }

    /**
     * Show create character form
     */
    @GetMapping("/characters/new")
    public String showCreateForm(Model model) {
        model.addAttribute("character", new DndCharacter());
        model.addAttribute("races", characterService.getAvailableRaces());
        model.addAttribute("classes", characterService.getAvailableClasses());
        model.addAttribute("pageTitle", "Create Character");
        return "character-form";
    }

    /**
     * Create a new character
     */
    @PostMapping("/characters")
    public String createCharacter(@Valid @ModelAttribute("character") DndCharacter character,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("races", characterService.getAvailableRaces());
            model.addAttribute("classes", characterService.getAvailableClasses());
            model.addAttribute("pageTitle", "Create Character");
            return "character-form";
        }
        characterService.saveCharacter(character);
        redirectAttributes.addFlashAttribute("successMessage", "Character created successfully!");
        return "redirect:/";
    }

    /**
     * View character details
     */
    @GetMapping("/characters/{id}")
    public String viewCharacter(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return characterService.getCharacterById(id)
                .map(character -> {
                    model.addAttribute("character", character);
                    model.addAttribute("pageTitle", character.getName());
                    return "character-details";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Character not found!");
                    return "redirect:/";
                });
    }

    /**
     * Show edit character form
     */
    @GetMapping("/characters/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return characterService.getCharacterById(id)
                .map(character -> {
                    model.addAttribute("character", character);
                    model.addAttribute("races", characterService.getAvailableRaces());
                    model.addAttribute("classes", characterService.getAvailableClasses());
                    model.addAttribute("pageTitle", "Edit " + character.getName());
                    return "character-form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Character not found!");
                    return "redirect:/";
                });
    }

    /**
     * Update a character
     */
    @PostMapping("/characters/{id}")
    public String updateCharacter(@PathVariable Long id,
                                  @Valid @ModelAttribute("character") DndCharacter character,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("races", characterService.getAvailableRaces());
            model.addAttribute("classes", characterService.getAvailableClasses());
            model.addAttribute("pageTitle", "Edit Character");
            return "character-form";
        }
        character.setId(id);
        characterService.saveCharacter(character);
        redirectAttributes.addFlashAttribute("successMessage", "Character updated successfully!");
        return "redirect:/characters/" + id;
    }

    /**
     * Delete a character
     */
    @PostMapping("/characters/{id}/delete")
    public String deleteCharacter(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        characterService.getCharacterById(id).ifPresent(character -> {
            characterService.deleteCharacter(id);
            redirectAttributes.addFlashAttribute("successMessage", "Character deleted successfully!");
        });
        return "redirect:/";
    }

    /**
     * Generate a random character
     */
    @PostMapping("/characters/generate")
    public String generateCharacter(RedirectAttributes redirectAttributes) {
        DndCharacter character = characterService.generateRandomCharacter();
        DndCharacter savedCharacter = characterService.saveCharacter(character);
        redirectAttributes.addFlashAttribute("successMessage", "Random character generated!");
        return "redirect:/characters/" + savedCharacter.getId();
    }
}
