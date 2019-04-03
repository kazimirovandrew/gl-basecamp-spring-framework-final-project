package com.basecamp.ice_and_fire.util;

import com.basecamp.ice_and_fire.domain.Character;
import com.basecamp.ice_and_fire.domain.House;
import com.basecamp.ice_and_fire.exception.NotFoundCharacterException;
import com.basecamp.ice_and_fire.repository.CharacterRepository;
import com.basecamp.ice_and_fire.repository.HouseRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class CharacterBuilder {

    private final RestTemplate restTemplate;
    private final CharacterRepository characterRepository;
    private final HouseRepository houseRepository;

    public Character build(String name) {

        Character character = new Character();

        character.setName(name);


        List<House> houses = getHouses(name);

        houses.forEach(house -> house.getCharacters().add(character));

        character.setHouses(houses);


        characterRepository.save(character);

        return character;
    }

    private List<House> getHouses(String name) {
        final String CHARACTER_URL = "https://www.anapioficeandfire.com/api/characters?name={name}";

        ResponseEntity<List<CharacterParser>> listResponseEntity =
                restTemplate.exchange(CHARACTER_URL, HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<CharacterParser>>() {
                        }, name);

        List<CharacterParser> characterParsers = listResponseEntity.getBody();

        if (characterParsers.isEmpty()) {
            throw new NotFoundCharacterException("Character with such name is not found!");

        } else {
            List<String> housesUrls = characterParsers.get(0).getUrls();

            return parseHousesUrls(housesUrls);
        }
    }

    private List<House> parseHousesUrls(List<String> urls) {

        List<House> houses = new ArrayList<>();

        for (String url : urls) {

            House house = houseRepository
                    .findByUrl(url)
                    .orElseGet(() -> restTemplate.getForObject(url, House.class));

            houses.add(house);
        }

        return houses;
    }
}
