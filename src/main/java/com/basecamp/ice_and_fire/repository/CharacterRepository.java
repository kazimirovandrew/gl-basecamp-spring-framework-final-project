package com.basecamp.ice_and_fire.repository;

import com.basecamp.ice_and_fire.domain.Character;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CharacterRepository extends CrudRepository<Character, UUID> {
    Optional<Character> findByName(String name);
}
