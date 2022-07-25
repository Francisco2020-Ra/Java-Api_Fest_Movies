package com.informatorio.festmovies.repository;

import com.informatorio.festmovies.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    Boolean existsCharacteryByName(String name);
}
