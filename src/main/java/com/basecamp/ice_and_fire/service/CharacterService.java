package com.basecamp.ice_and_fire.service;

import com.basecamp.ice_and_fire.domain.Character;
import com.basecamp.ice_and_fire.domain.House;
import com.basecamp.ice_and_fire.exception.NotFoundCharacterException;
import com.basecamp.ice_and_fire.repository.CharacterRepository;
import com.basecamp.ice_and_fire.repository.HouseRepository;
import com.basecamp.ice_and_fire.util.CharacterBuilder;
import com.basecamp.ice_and_fire.util.CharacterParser;
import com.basecamp.ice_and_fire.wire.CreateCharacterResponse;
import com.basecamp.ice_and_fire.wire.GetCharacterByIdResponse;
import com.basecamp.ice_and_fire.wire.GetCharactersResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Service
public class CharacterService {

    private final CharacterBuilder characterBuilder;
    private final CharacterRepository characterRepository;

    public CreateCharacterResponse createCharacter(String name) {

        CreateCharacterResponse response = new CreateCharacterResponse();

        Character character = characterRepository
                .findByName(name)
                .orElseGet(() -> characterBuilder.build(name));

        response.setId(character.getId());

        return response;
    }

    public GetCharacterByIdResponse getCharacterById(UUID id) {

        GetCharacterByIdResponse response = new GetCharacterByIdResponse();

        Character character = characterRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundCharacterException("Character with such id is not found!"));

        response.setMessage(character.getName(), character.getHouses());

        return response;
    }

    public GetCharactersResponse getCharacters() {

        GetCharactersResponse response = new GetCharactersResponse();

        for (Character character : characterRepository.findAll()) {
            response.getNameToId().put(character.getName(), character.getId());
        }

        return response;
    }
}
