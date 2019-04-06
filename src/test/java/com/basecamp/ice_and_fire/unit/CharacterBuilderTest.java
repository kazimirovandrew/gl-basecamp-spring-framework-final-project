package com.basecamp.ice_and_fire.unit;

import com.basecamp.ice_and_fire.config.TestConfig;
import com.basecamp.ice_and_fire.domain.Character;
import com.basecamp.ice_and_fire.domain.House;
import com.basecamp.ice_and_fire.repository.CharacterRepository;
import com.basecamp.ice_and_fire.util.CharacterBuilder;
import com.basecamp.ice_and_fire.util.CharacterParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@Import(TestConfig.class)
public class CharacterBuilderTest {

    private final String NAME = "Jon Snow";
    private final Character character = new Character();

    @Autowired
    private CharacterBuilder characterBuilder;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CharacterRepository characterRepository;

    @Before
    public void setUp() {
        String HOUSE_URL = "https://www.anapioficeandfire.com/api/houses/362";
        String CHARACTER_URL = "https://www.anapioficeandfire.com/api/characters?name={name}";

        List<String> housesUrls = Arrays.asList(HOUSE_URL);

        House house = new House();
        house.setUrl(HOUSE_URL);

        character.setName(NAME);
        character.setHouses(Arrays.asList(house));

        CharacterParser parser = new CharacterParser();
        parser.setUrls(housesUrls);
        List<CharacterParser> parsers = Arrays.asList(parser);

        when(restTemplate.exchange(CHARACTER_URL, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<CharacterParser>>() {
                }, NAME)).thenReturn(new ResponseEntity<>(parsers, HttpStatus.OK));

        when(restTemplate.getForObject(HOUSE_URL, House.class))
                .thenReturn(house);

        when(characterRepository.save(any(Character.class))).thenReturn(character);
    }


    @Test
    public void givenName_whenBuild_thenReturnCharacter() {

        //when
        Character found = characterBuilder.build(NAME);

        //then
        assertEquals(character, found);
    }
}
