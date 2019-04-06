package com.basecamp.ice_and_fire.config;

import com.basecamp.ice_and_fire.repository.CharacterRepository;
import com.basecamp.ice_and_fire.repository.HouseRepository;
import com.basecamp.ice_and_fire.service.CharacterService;
import com.basecamp.ice_and_fire.util.CharacterBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class TestConfig {

    @Bean
    public CharacterService characterService(CharacterBuilder characterBuilder,
                                             CharacterRepository characterRepository) {

        return new CharacterService(characterBuilder, characterRepository);
    }

    @Bean
    public CharacterBuilder characterBuilder(RestTemplate restTemplate,
                                             CharacterRepository characterRepository,
                                             HouseRepository houseRepository){

        return new CharacterBuilder(restTemplate, characterRepository, houseRepository);
    }

    @MockBean
    private RestTemplate restTemplate;

//    @MockBean
//    private CharacterBuilder characterBuilder;

    @MockBean
    private CharacterRepository characterRepository;

    @MockBean
    private HouseRepository houseRepository;
}
