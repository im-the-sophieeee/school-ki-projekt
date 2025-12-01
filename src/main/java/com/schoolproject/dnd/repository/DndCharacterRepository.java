package com.schoolproject.dnd.repository;

import com.schoolproject.dnd.model.DndCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for DndCharacter entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface DndCharacterRepository extends JpaRepository<DndCharacter, Long> {

    /**
     * Find all characters by race.
     */
    List<DndCharacter> findByRace(String race);

    /**
     * Find all characters by character class.
     */
    List<DndCharacter> findByCharacterClass(String characterClass);

    /**
     * Find all characters by level.
     */
    List<DndCharacter> findByLevel(int level);

    /**
     * Find characters by name containing (case insensitive).
     */
    List<DndCharacter> findByNameContainingIgnoreCase(String name);

    /**
     * Find all distinct races.
     */
    @Query("SELECT DISTINCT c.race FROM DndCharacter c")
    List<String> findDistinctRaces();

    /**
     * Find all distinct character classes.
     */
    @Query("SELECT DISTINCT c.characterClass FROM DndCharacter c")
    List<String> findDistinctCharacterClasses();
}
