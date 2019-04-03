package com.basecamp.ice_and_fire.controller;

import com.basecamp.ice_and_fire.service.CharacterService;
import com.basecamp.ice_and_fire.wire.CreateCharacterResponse;
import com.basecamp.ice_and_fire.wire.GetCharacterByIdResponse;
import com.basecamp.ice_and_fire.wire.GetCharactersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@RestController
@Validated
public class CharacterController {

    private static final int ID_LENGTH = 36;
    private static final String SIZE_MESSAGE = "Invalid id length!";
    private static final String BLANK_MESSAGE = "Name must not be blank!";

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping("/character")
    public CreateCharacterResponse createCharacter(
            @NotBlank(message = BLANK_MESSAGE)
            @RequestParam String name) {

        return characterService.createCharacter(name);
    }

    @GetMapping("/characters/{id}")
    public GetCharacterByIdResponse getCharacterById(
            @Size(min = ID_LENGTH, max = ID_LENGTH, message = SIZE_MESSAGE)
            @PathVariable String id) {

        return characterService.getCharacterById(UUID.fromString(id));
    }

    @GetMapping("/characters")
    public GetCharactersResponse getCharacters() {

        return characterService.getCharacters();
    }
}
