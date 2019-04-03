package com.basecamp.ice_and_fire.unit;

import com.basecamp.ice_and_fire.config.TestConfig;
import com.basecamp.ice_and_fire.domain.Character;
import com.basecamp.ice_and_fire.exception.NotFoundCharacterException;
import com.basecamp.ice_and_fire.repository.CharacterRepository;
import com.basecamp.ice_and_fire.service.CharacterService;
import com.basecamp.ice_and_fire.wire.CreateCharacterResponse;
import com.basecamp.ice_and_fire.wire.GetCharacterByIdResponse;
import com.basecamp.ice_and_fire.wire.GetCharactersResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@Import(TestConfig.class)
public class CharacterServiceTest {
    private static final String NAME = "Jon Snow";
    private static final UUID ID = UUID.randomUUID();
    private static final Character character = new Character();

    @Autowired
    private CharacterService characterService;

    @Autowired
    private CharacterRepository characterRepository;

    @BeforeClass
    public static void setUp() {
        character.setId(ID);
        character.setName(NAME);
    }

    @Test
    public void givenName_whenCreateCharacter_thenVerifyResponse() {

        when(characterRepository.findByName(NAME))
                .thenReturn(Optional.of(character));

        //when
        CreateCharacterResponse found = characterService.createCharacter(NAME);

        //then
        assertEquals(character.getId(), found.getId());
    }

    @Test
    public void givenValidId_whenGetCharacterById_thenVerifyResponseMessage() {
        final String MESSAGE = "Jon Snow is a homeless";

        when(characterRepository.findById(ID))
                .thenReturn(Optional.of(character));

        //when
        GetCharacterByIdResponse found = characterService.getCharacterById(ID);

        //then
        assertEquals(MESSAGE, found.getMessage());
    }

    @Test(expected = NotFoundCharacterException.class)
    public void givenInvalidId_whenGetCharacterById_thenThrowException() {
        //when
        characterService.getCharacterById(ID);
    }

    @Test
    public void whenGetCharacters_thenVerifyResponseMap() {
        final Map<String, UUID> map = new HashMap<>();
        map.put(NAME, ID);

        List<Character> characters = new ArrayList<>();
        characters.add(character);

        when(characterRepository.findAll())
                .thenReturn(characters);

        //when
        GetCharactersResponse found = characterService.getCharacters();

        //then
        assertEquals(map, found.getNameToId());
    }
}
